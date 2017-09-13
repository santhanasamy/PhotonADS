
package com.photon.codechallenge.shortestpath;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

    private List<String> values;

    public GridAdapter(List<String> aData) {
        values = aData;
    }

    @Override
    public GridViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View layoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.grid_item, null);
        return new GridViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder( GridViewHolder holder, int position ) {

        final String name = values.get(position);

        if (null != mSelectedResult
                && CommonUtils.CHOSEN_PATH_INDICATOR == mSelectedResult.get(position)) {
            holder.mGridItemTxtView.setBackgroundColor(Color.RED);
        } else {
            holder.mGridItemTxtView.setBackgroundColor(Color.WHITE);
        }
        holder.mGridItemTxtView.setText(name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void add( int position, String item ) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove( int position ) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    private List<Integer> mSelectedResult;

    public void updateProgress( int[][] aResult ) {

        mSelectedResult = CommonUtils.convertArrayToIntList(aResult);
        notifyDataSetChanged();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        public TextView mGridItemTxtView;

        public GridViewHolder(View aView) {
            super(aView);
            mGridItemTxtView = aView.findViewById(R.id.grid_item);
        }
    }

}
