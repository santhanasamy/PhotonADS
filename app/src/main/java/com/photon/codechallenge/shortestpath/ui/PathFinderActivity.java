
package com.photon.codechallenge.shortestpath.ui;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.photon.codechallenge.shortestpath.R;
import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.LowCostPathFinder;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;
import com.photon.codechallenge.shortestpath.utils.UiUtils;

import java.util.Map;

/**
 * Activity to find the low cost path by moving across the 2D-Input grid.
 */
public class PathFinderActivity extends AppCompatActivity {

    private static final int UPDATE_RESULT = 100;

    private static final int UPDATE_PROGRESS = 101;

    private RecyclerView mRecyclerView = null;

    private TextView mResultStatusTxtView = null;

    private TextView mResultCostTxtView = null;

    private TextView mResultPathTxtView = null;

    private Button mCalculateButton = null;

    private Button mClearButton = null;

    private LinearLayout mResultContainer = null;

    private LowCostPathAdapter mAdapter = null;

    private RecyclerView.LayoutManager mLayoutManager = null;

    private Handler mShortestPathHandler = null;

    private Handler mUIHandler = null;

    private int[][] mInput = null;

    private int mNoOfRows = 0;

    private int mNoOfColumns = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultStatusTxtView = (TextView) findViewById(R.id.result_status_txt_view);
        mResultCostTxtView = (TextView) findViewById(R.id.result_cost_txt_view);
        mResultPathTxtView = (TextView) findViewById(R.id.result_path_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.input_grid_view);
        mRecyclerView.setHasFixedSize(true);

        mCalculateButton = (Button) findViewById(R.id.calculate_btn);
        mClearButton = (Button) findViewById(R.id.clear_btn);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {

                Message lMsg = mShortestPathHandler.obtainMessage();
                mInput = CommonUtils
                        .convertListToIntArray(mAdapter.getData(), mNoOfRows, mNoOfColumns);
                lMsg.obj = mInput;
                mShortestPathHandler.sendMessage(lMsg);
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View aView ) {
                mAdapter.clear();
                mResultStatusTxtView.setText("");
                mResultCostTxtView.setText("");
                mResultPathTxtView.setText("");
            }
        });

        mResultContainer = (LinearLayout) findViewById(R.id.result_container);

        mUIHandler = new Handler() {
            @Override
            public void handleMessage( Message msg ) {

                if (msg.what == UPDATE_RESULT) {
                    updateUI(msg.arg1);
                    updateProgress((Map<Integer, String>) msg.obj, mNoOfRows, mNoOfColumns);
                } else if (msg.what == UPDATE_PROGRESS) {
                    Map<Integer, String> aResultIndex = (Map<Integer, String>) msg.obj;
                    updateProgress(aResultIndex, msg.arg1, msg.arg2);
                }
            }
        };

        int[][] lInput = (int[][]) getIntent().getExtras().getSerializable(
                InputCollectorActivity.KEY_INPUT_EXTRA);

        mInput = SampleInputs.DEFAULT_MATRIX;
        if (null != lInput) {
            mInput = lInput;
        }
        mNoOfRows = mInput.length;
        mNoOfColumns = mInput[0].length;
        prepareUI(mInput);

    }

    /**
     * Initialize the UI with the supplied 2D matrix.
     *
     * @param aData 2D matrix
     */
    private void prepareUI( int[][] aData ) {

        if (aData == null || aData.length == 0 || aData.length > CommonUtils.UI_MAX_ROW) {
            UiUtils.showInvalidWarning(this);
            finish();
            return;
        }

        if (aData.length > 0 && aData[0].length > CommonUtils.UI_MAX_COLUMN) {
            UiUtils.showInvalidWarning(this);
            finish();
            return;
        }
        mAdapter = new LowCostPathAdapter(this, CommonUtils.convertArrayToList(aData));

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(this, mInput[0].length);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        mRecyclerView
                .addItemDecoration(new SpacingDecoration(mInput[0].length, spacingInPixels, true));

        HandlerThread lThread = new HandlerThread("Shortest Path Finder");
        lThread.start();
        mShortestPathHandler = new Handler(lThread.getLooper()) {
            @Override
            public void handleMessage( Message msg ) {
                int lResult = LowCostPathFinder
                        .findShortestPath((int[][]) msg.obj, new UIProgressListener() {

                            @Override
                            public void onProgressUpdate(
                                    Map<Integer, String> aResultIndex,
                                    int aRowCount,
                                    int aColumnCount ) {

                                Message lMsg = mUIHandler.obtainMessage(UPDATE_PROGRESS);
                                lMsg.obj = aResultIndex;
                                lMsg.arg1 = aRowCount;
                                lMsg.arg2 = aColumnCount;
                                mUIHandler.sendMessageDelayed(lMsg, 1000);

                            }
                        });

                Message lMsg = mUIHandler.obtainMessage(UPDATE_RESULT);
                lMsg.arg1 = lResult;
                lMsg.obj = LowCostPathFinder.getResultIndex();
                mUIHandler.sendMessage(lMsg);
            }
        };

        Message lMsg = mShortestPathHandler.obtainMessage();
        lMsg.obj = aData;

        mShortestPathHandler.sendMessage(lMsg);

    }

    /**
     * Method to update the result in the UI
     *
     * @param aResultCost Minimum cost.
     */
    private void updateUI( int aResultCost ) {
        mResultStatusTxtView.setText("" + LowCostPathFinder.getStatus());
        mResultCostTxtView.setText("" + aResultCost);
        mResultPathTxtView.setText("" + LowCostPathFinder.getPath());

    }

    /**
     * Update the minimum cost finding progress in the UI.
     *
     * @param aProgress The path which is identified in the current moment,
     *            represented as a Map
     * @param aRowCount No of rows.
     * @param aColumnCount No of columns.
     */
    private void updateProgress( Map<Integer, String> aProgress, int aRowCount, int aColumnCount ) {

        int[][] result = new int[aRowCount][aColumnCount];
        if (null == aProgress) {
            mAdapter.updateProgress(result);
            return;
        }
        for (String aData : aProgress.values()) {

            String[] lData = aData.split("--");
            int lRow = Integer.parseInt(lData[0]);
            int lColumn = Integer.parseInt(lData[1]);
            result[lRow - 1][lColumn - 1] = CommonUtils.CHOSEN_PATH_INDICATOR;
            mAdapter.updateProgress(result);
        }
    }

    /**
     * Decorated to beautify the grids in the recycler view.
     */
    private static class SpacingDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;

        private int spacing;

        private boolean includeEdge;

        public SpacingDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(
                Rect outRect,
                View view,
                RecyclerView parent,
                RecyclerView.State state ) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
