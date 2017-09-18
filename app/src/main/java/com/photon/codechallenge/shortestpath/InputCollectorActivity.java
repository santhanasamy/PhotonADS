
package com.photon.codechallenge.shortestpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import java.util.HashMap;

/**
 *
 *
 */
public class InputCollectorActivity extends AppCompatActivity {

    public static final String KEY_INPUT_EXTRA = "Input Data";

    private EditText mNoOfRowsEditTextView = null;

    private EditText mNoOfColumnsEditTextView = null;

    private Button mCalculateButton = null;

    private Button mClearButton = null;

    private Button mGenerateBtn = null;

    private LinearLayout mInputContainer = null;

    private int mInput[][] = null;

    private HashMap<Integer, String> mInputMap;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_collector);

        mNoOfRowsEditTextView = (EditText) findViewById(R.id.rows_edit_txt_view);
        mNoOfColumnsEditTextView = (EditText) findViewById(R.id.columns_edit_txt_view);

        mGenerateBtn = (Button) findViewById(R.id.set_btn);

        mGenerateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View view ) {

                String lStrRowCount = mNoOfRowsEditTextView.getText().toString();
                String lStrColumnCount = mNoOfColumnsEditTextView.getText().toString();

                int lNoOfRows = 0;
                int lNoOfColumns = 0;

                if (!TextUtils.isEmpty(lStrRowCount) && TextUtils.isDigitsOnly(lStrRowCount)) {
                    lNoOfRows = Integer.parseInt(lStrRowCount);
                }

                if (!TextUtils.isEmpty(lStrColumnCount)
                        && TextUtils.isDigitsOnly(lStrColumnCount)) {
                    lNoOfColumns = Integer.parseInt(lStrColumnCount);
                }
                mInput = new int[lNoOfRows][lNoOfColumns];

                if(null == mInputMap) {
                    mInputMap = new HashMap<Integer, String>(lNoOfRows);
                } else {
                    HashMap<Integer, String> lInputMap = new HashMap<Integer, String>(lNoOfRows);
                    for (Integer lInt: mInputMap.keySet()) {
                        lInputMap.put(lInt, mInputMap.get(lInt));
                    }
                    mInputMap = lInputMap;
                }
                insertRows(lNoOfRows);
            }
        });
        mNoOfRowsEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void afterTextChanged( Editable editable ) {

                String lRowCount = editable.toString();

                if (!TextUtils.isEmpty(lRowCount)) {
                    int lTempCount = Integer.parseInt(lRowCount);

                    if (lTempCount > CommonUtils.MAX_NO_OF_ROWS) {
                        lTempCount = CommonUtils.MAX_NO_OF_ROWS;
                    }

                    mNoOfRowsEditTextView.removeTextChangedListener(this);
                    mNoOfRowsEditTextView.setText("" + lTempCount);
                    mNoOfRowsEditTextView.addTextChangedListener(this);
                }
            }
        });

        mNoOfColumnsEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void afterTextChanged( Editable editable ) {

                String lColumnCount = editable.toString();

                if (!TextUtils.isEmpty(lColumnCount)) {
                    int lTempCount = Integer.parseInt(lColumnCount);

                    if (lTempCount > CommonUtils.MAX_NO_OF_COLUMNS) {
                        lTempCount = CommonUtils.MAX_NO_OF_COLUMNS;
                    }
                    mNoOfColumnsEditTextView.removeTextChangedListener(this);
                    mNoOfColumnsEditTextView.setText("" + lTempCount);
                    mNoOfColumnsEditTextView.addTextChangedListener(this);
                }
            }
        });
        mInputContainer = (LinearLayout) findViewById(R.id.input_container);

        mCalculateButton = (Button) findViewById(R.id.calculate_btn);
        mClearButton = (Button) findViewById(R.id.clear_btn);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {

                for (Integer aRowIdx : mInputMap.keySet()) {

                    String[] lValues = mInputMap.get(aRowIdx).split(",");

                    for (int i = 0; i < lValues.length; i++) {

                        if (i >= mInput[i].length) {
                            continue;
                        }
                        String lStr = lValues[i];

                        if (!TextUtils.isEmpty(lStr) && TextUtils.isDigitsOnly(lStr)) {
                            mInput[aRowIdx][i] = Integer.parseInt(lStr);
                        } else {
                            mInput[aRowIdx][i] = 0;
                        }
                    }
                }
                Intent lIntent = new Intent(InputCollectorActivity.this, MainActivity.class);
                Bundle lBundle = new Bundle();
                lBundle.putSerializable(InputCollectorActivity.KEY_INPUT_EXTRA, mInput);
                lIntent.putExtras(lBundle);
                startActivity(lIntent);
                // finish();
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {
                mNoOfRowsEditTextView.setText("");
                mNoOfColumnsEditTextView.setText("");
                insertRows(0);
            }
        });

    }

    private void insertRows( int aNoOfRows ) {

        if (aNoOfRows == 0) {
            mInputContainer.removeAllViews();
        }

        int lCount = mInputContainer.getChildCount();

        if (lCount < aNoOfRows) {

            LinearLayout.LayoutParams lLayoutParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lLayoutParam.setMargins(10, 10, 10, 10);

            for (int i = lCount; i < aNoOfRows; i++) {

                final EditText lEditText = new EditText(this);
                lEditText.setTag(i);
                lEditText.setLayoutParams(lLayoutParam);
                lEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789,"));
                lEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(
                            CharSequence charSequence,
                            int i,
                            int i1,
                            int i2 ) {

                    }

                    @Override
                    public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                    }

                    @Override
                    public void afterTextChanged( Editable editable ) {

                        int lRowIdx = (int) lEditText.getTag();
                        mInputMap.put(lRowIdx, editable.toString());
                    }
                });
                mInputContainer.addView(lEditText, i);
            }
        } else if (lCount > aNoOfRows) {
            mInputContainer.removeViews(aNoOfRows, lCount - aNoOfRows);
        }
    }
}
