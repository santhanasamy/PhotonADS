
package com.photon.codechallenge.shortestpath.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photon.codechallenge.shortestpath.R;
import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.UiUtils;

import java.util.List;

/**
 * Adapter provide view for the 2D-Grid-RecyclerView and bind data with the
 * view.
 */
public class LowCostPathAdapter extends RecyclerView.Adapter<LowCostPathAdapter.GridViewHolder> {

    private List<String> mCostList = null;

    private Context mContext = null;

    private List<Integer> mSelectedResult = null;

    public LowCostPathAdapter(Context aContext, List<String> aData) {
        mCostList = aData;
        mContext = aContext;
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
            holder.mGridItemTxtView.setBackgroundColor(Color.RED);
        } else {
            holder.mGridItemTxtView.setBackgroundColor(Color.WHITE);
        }

        int lValue = Integer.parseInt(name);
        holder.mGridItemTxtView.setTextSize(UiUtils.getTextSizeBasedOnTextLength(mContext, lValue));

        TextWatcher lTempWatcher = (TextWatcher) holder.mGridItemTxtView.getTag();

        if (null == lTempWatcher) {

            TextWatcher lWatcher = new TextWatcher() {

                @Override
                public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                }

                @Override
                public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

                }

                @Override
                public void afterTextChanged( Editable editable ) {
                    mCostList.set(position, editable.toString());
                }
            };
            holder.mGridItemTxtView.setText(name);
            holder.mGridItemTxtView.addTextChangedListener(lWatcher);
            holder.mGridItemTxtView.setTag(lWatcher);
        } else {
            holder.mGridItemTxtView.removeTextChangedListener(lTempWatcher);
            holder.mGridItemTxtView.setText(name);
            holder.mGridItemTxtView.addTextChangedListener(lTempWatcher);
        }

        holder.mGridItemTxtView.setTag(R.id.grid_item, position);
    }

    /**
     * Return no of items in the adapter.
     * 
     * @return
     */
    @Override
    public int getItemCount() {
        return mCostList.size();
    }

    /**
     * Method to get the data set used by the adapter.
     *
     * @return List of cost.
     */
    public List<String> getData() {
        return mCostList;
    }

    /**
     * Method to add an item into the recycler view
     * 
     * @param position Position of the item
     * @param item Item to be added
     */
    public void add( int position, String item ) {
        mCostList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Method to remove an item into the recycler view
     * 
     * @param position Position of the item
     */
    public void remove( int position ) {
        mCostList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Method to feed the cost finding progress updates from the background to
     * UI(RecyclerView)
     *
     * @param aResult 2D Matrix representing the lowest cost path. In the supplied
     *            matrix, Cells with the value "999" is used to traverse along the
     *            path.
     */
    public void updateProgress( int[][] aResult ) {

        mSelectedResult = CommonUtils.convertArrayToIntList(aResult);
        notifyDataSetChanged();
    }

    /**
     * Method to clear the adapter and reset the cost values with the default value
     * "0"
     */
    public void clear() {

        mSelectedResult = null;
        for (int i = 0; i < mCostList.size(); i++) {
            mCostList.set(i, "0");
        }
        notifyDataSetChanged();
    }

    /**
     * View holder
     */
    public static class GridViewHolder extends RecyclerView.ViewHolder {

        public TextView mGridItemTxtView;

        public GridViewHolder(View aView) {
            super(aView);
            mGridItemTxtView = (TextView) aView.findViewById(R.id.grid_item);
        }
    }
}
