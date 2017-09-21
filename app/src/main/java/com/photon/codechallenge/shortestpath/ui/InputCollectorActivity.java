
package com.photon.codechallenge.shortestpath.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.photon.codechallenge.shortestpath.R;
import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.UiUtils;

import java.util.HashMap;

/**
 * Activity to collect inputs from the User.
 */
public class InputCollectorActivity extends AppCompatActivity {

    public static final String KEY_INPUT_EXTRA = "Input Data";

    private EditText mNoOfRowsView = null;

    private EditText mNoOfColumnsView = null;

    private Button mCalculateButton = null;

    private Button mClearButton = null;

    private Button mGenerateBtn = null;

    private LinearLayout mInputContainer = null;

    private HashMap<Integer, String> mInputMap = null;

    private int mInput[][] = null;

    private int mNoOfRows = 0;

    private int mNoOfColumns = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_collector);

        mNoOfRowsView = (EditText) findViewById(R.id.rows_edit_txt_view);
        mNoOfColumnsView = (EditText) findViewById(R.id.columns_edit_txt_view);

        mInputContainer = (LinearLayout) findViewById(R.id.input_container);

        mCalculateButton = (Button) findViewById(R.id.calculate_btn);
        mClearButton = (Button) findViewById(R.id.clear_btn);
        mGenerateBtn = (Button) findViewById(R.id.generate_row_btn);

        mNoOfRowsView.addTextChangedListener(new TextWatcher() {
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

                        UiUtils.showToast(
                                InputCollectorActivity.this,
                                getResources().getString(
                                        R.string.max_allowed_rows,
                                        CommonUtils.UI_MAX_ROW));
                        lTempCount = CommonUtils.MAX_NO_OF_ROWS;
                    }

                    if (lTempCount != mNoOfColumns) {
                        mCalculateButton.setEnabled(false);
                    }
                    mNoOfRowsView.removeTextChangedListener(this);
                    mNoOfRowsView.setText("" + lTempCount);
                    mNoOfRowsView.addTextChangedListener(this);
                    mNoOfRowsView.setSelection(mNoOfRowsView.getText().length());

                }
            }
        });

        mNoOfColumnsView.addTextChangedListener(new TextWatcher() {
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

                    if (lTempCount > CommonUtils.UI_MAX_COLUMN) {

                        UiUtils.showToast(
                                InputCollectorActivity.this,
                                getResources().getString(
                                        R.string.max_allowed_Columns,
                                        CommonUtils.UI_MAX_COLUMN));
                        lTempCount = CommonUtils.UI_MAX_COLUMN;
                    }

                    if (lTempCount != mNoOfColumns) {
                        mCalculateButton.setEnabled(false);
                    }
                    mNoOfColumnsView.removeTextChangedListener(this);
                    mNoOfColumnsView.setText("" + lTempCount);
                    mNoOfColumnsView.addTextChangedListener(this);
                    mNoOfColumnsView.setSelection(mNoOfColumnsView.getText().length());
                }
            }
        });
        mGenerateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View view ) {

                String lStrRowCount = mNoOfRowsView.getText().toString();
                String lStrColumnCount = mNoOfColumnsView.getText().toString();

                if (!TextUtils.isEmpty(lStrRowCount) && TextUtils.isDigitsOnly(lStrRowCount)) {
                    mNoOfRows = Integer.parseInt(lStrRowCount);
                }

                if (!TextUtils.isEmpty(lStrColumnCount)
                        && TextUtils.isDigitsOnly(lStrColumnCount)) {
                    mNoOfColumns = Integer.parseInt(lStrColumnCount);
                }

                if (0 == mNoOfRows || 0 == mNoOfColumns) {
                    UiUtils.showInvalidWarning(InputCollectorActivity.this);
                    return;
                }
                mInput = new int[mNoOfRows][mNoOfColumns];

                if (null == mInputMap) {
                    mInputMap = new HashMap<>(mNoOfRows);
                } else {
                    HashMap<Integer, String> lInputMap = new HashMap<>(mNoOfRows);
                    for (Integer lInt : mInputMap.keySet()) {
                        lInputMap.put(lInt, mInputMap.get(lInt));
                    }
                    mInputMap = lInputMap;
                }
                insertRows(mNoOfRows);
                mCalculateButton.setEnabled(true);
            }
        });

        mCalculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {

                if (null == mInputMap || 0 == mInputMap.size()) {
                    UiUtils.showInvalidWarning(InputCollectorActivity.this);
                    return;
                }
                for (Integer aRowIdx : mInputMap.keySet()) {

                    String[] lValues = mInputMap.get(aRowIdx).split(",");

                    if (aRowIdx >= mInput.length) {
                        continue;
                    }
                    for (int i = 0; i < lValues.length; i++) {

                        if (i >= mInput[aRowIdx].length) {
                            continue;
                        }
                        String lStr = lValues[i];

                        if (!TextUtils.isEmpty(lStr) && UiUtils.isValidInput(lStr)) {
                            mInput[aRowIdx][i] = Integer.parseInt(lStr);
                        } else {
                            mInput[aRowIdx][i] = 0;
                        }
                    }
                }

                Intent lIntent = new Intent(InputCollectorActivity.this, PathFinderActivity.class);
                Bundle lBundle = new Bundle();
                lBundle.putSerializable(InputCollectorActivity.KEY_INPUT_EXTRA, mInput);
                lIntent.putExtras(lBundle);
                startActivity(lIntent);
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {
                mNoOfRowsView.setText("");
                mNoOfColumnsView.setText("");
                insertRows(0);
            }
        });

    }

    /**
     * Method to insert rows based on the user given no of rows.
     * 
     * @param aNoOfRows No of rows to be inserted.
     */
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
                lEditText.setId(i);
                lEditText.setTag(i);
                lEditText.setLayoutParams(lLayoutParam);
                lEditText.setInputType(
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                lEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789,-+"));
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
