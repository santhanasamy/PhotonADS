
package com.photon.codechallenge.shortestpath.ui;

import java.util.Map;

/**
 * Listener to notify the progress of finding the shortest/low cost path while
 * moving across the grid.
 */
public interface UIProgressListener {
    /**
     * Intimate the path finding progress.
     *
     * @param aResultIndex Current matrix state
     * @param aRowCount No of rows in the input matrix
     * @param aColumnCount No of columns in the input matrix
     */
    void onProgressUpdate( Map<Integer, String> aResultIndex, int aRowCount, int aColumnCount );
}
