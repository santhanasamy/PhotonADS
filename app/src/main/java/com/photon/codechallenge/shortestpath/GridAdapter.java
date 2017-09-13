
package com.photon.codechallenge.shortestpath;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import java.util.List;

/**
 *
 *
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private List<String> mCostList;

    public GridAdapter(List<String> aData) {
        mCostList = aData;
    }

    @Override
    public GridViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View layoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.grid_item, null);
        return new GridViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder( final GridViewHolder holder, final int position ) {

        final String name = mCostList.get(position);

        if (null != mSelectedResult
                && CommonUtils.CHOSEN_PATH_INDICATOR == mSelectedResult.get(position)) {
            holder.mRootView.setBackgroundColor(Color.RED);
        } else {
            holder.mRootView.setBackgroundColor(Color.WHITE);
        }

        TextWatcher lWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

            }

            @Override
            public void afterTextChanged( Editable editable ) {
                // int lRow = position / CommonUtils.UI_MAX_COLUMN;
                // int lColumn = position % CommonUtils.UI_MAX_COLUMN;
                // System.out.println("[R,C][" + lRow +"," + lColumn +"]");
                mCostList.set(position, editable.toString());
            }
        };
        holder.mGridItemTxtView.addTextChangedListener(lWatcher);
        holder.mGridItemTxtView.setTag(lWatcher);
        holder.mGridItemTxtView.setTag(R.id.grid_item, position);
        holder.mGridItemTxtView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCostList.size();
    }

    public List<String> getData() {
        return mCostList;
    }

    public void add( int position, String item ) {
        mCostList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove( int position ) {
        mCostList.remove(position);
        notifyItemRemoved(position);
    }

    private List<Integer> mSelectedResult;

    public void updateProgress( int[][] aResult ) {

        mSelectedResult = CommonUtils.convertArrayToIntList(aResult);
        notifyDataSetChanged();
    }

    public void clear() {

        mSelectedResult = null;
        for (int i = 0; i < mCostList.size(); i++) {
            mCostList.set(i, "0");
        }
        notifyDataSetChanged();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {

        public TextView mGridItemTxtView;

        public View mRootView;

        public GridViewHolder(View aView) {
            super(aView);
            mRootView = aView;
            mGridItemTxtView = aView.findViewById(R.id.grid_item);
        }
    }
}
