
package com.photon.codechallenge.shortestpath;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView = null;

    private TextView mResultStatusTxtView = null;

    private TextView mResultCostTxtView = null;

    private TextView mResultPathTxtView = null;

    private GridAdapter mAdapter = null;

    private RecyclerView.LayoutManager mLayoutManager = null;

    private Handler mShortestPathHandler = null;

    private Handler mUIHandler = null;

    private static final int UPDATE_RESULT = 100;

    private static final int UPDATE_PROGRESS = 101;

    private static final int[][] TEST_CASE_1 = new int[][] {
            {
                    3, 4, 1, 2, 8, 6
            }, {
                    6, 1, 8, 2, 7, 4
            }, {
                    5, 9, 3, 9, 9, 5
            }, {
                    8, 4, 1, 3, 2, 6
            }, {
                    3, 7, 2, 8, 6, 4
            },
    };

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultStatusTxtView = (TextView) findViewById(R.id.result_status_txt_view);
        mResultCostTxtView = (TextView) findViewById(R.id.result_cost_txt_view);
        mResultPathTxtView = (TextView) findViewById(R.id.result_path_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.input_grid_view);
        mRecyclerView.setHasFixedSize(true);

        mUIHandler = new Handler() {
            @Override
            public void handleMessage( Message msg ) {

                if (msg.what == UPDATE_RESULT) {
                    updateUI((Integer) msg.obj);
                } else if (msg.what == UPDATE_PROGRESS) {
                    Map<Integer, String> aResultIndex = (Map<Integer, String>) msg.obj;
                    updateProgress(aResultIndex, msg.arg1, msg.arg2);
                }
            }
        };
        prepareUI(TEST_CASE_1);

    }

    private void prepareUI( int[][] aData ) {

        if (aData == null || aData.length == 0 || aData.length > CommonUtils.UI_MAX_ROW) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }

        if (aData.length > 0 && aData[0].length > CommonUtils.UI_MAX_ROW) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
        mAdapter = new GridAdapter(CommonUtils.convertArrayToList(aData));

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(this, TEST_CASE_1.length + 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        HandlerThread lThread = new HandlerThread("Shortest Path Finder");
        lThread.start();
        mShortestPathHandler = new Handler(lThread.getLooper()) {
            @Override
            public void handleMessage( Message msg ) {
                int lResult = CommonUtils
                        .findShortestPath((int[][]) msg.obj, new CommonUtils.UIProgressListener() {

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
                lMsg.obj = lResult;
                mUIHandler.sendMessage(lMsg);
            }
        };

        Message lMsg = mShortestPathHandler.obtainMessage();
        lMsg.obj = aData;

        mShortestPathHandler.sendMessage(lMsg);

    }

    private void updateUI( int aResultCost ) {
        mResultStatusTxtView.setText("" + CommonUtils.getStatus());
        mResultCostTxtView.setText("" + aResultCost);
        mResultPathTxtView.setText("" + CommonUtils.getPath());

    }

    private void updateProgress( Map<Integer, String> aProgress, int aRowCount, int aColumnCount ) {

        int[][] result = new int[aRowCount][aColumnCount];

        for (String aData : aProgress.values()) {

            String[] lData = aData.split("--");
            int lRow = Integer.parseInt(lData[0]);
            int lColumn = Integer.parseInt(lData[1]);
            result[lRow - 1][lColumn - 1] = CommonUtils.CHOSEN_PATH_INDICATOR;
            mAdapter.updateProgress(result);
        }

    }

}
