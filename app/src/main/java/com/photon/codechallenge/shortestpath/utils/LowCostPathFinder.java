
package com.photon.codechallenge.shortestpath.utils;

import com.photon.codechallenge.shortestpath.ui.UIProgressListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to find the low cost path when moving across a 2D-Input grid.
 * <br/>
 * 1. The first line is either “Yes” or “No”, to indicate the path made it all
 * the way through the grid <br/>
 * 2. The second line is the total cost. <br/>
 * 3. The third line shows the path taken as a sequence of 'n' delimited
 * integers, each representing the rows traversed in turn. If there is more than
 * one path of least cost, only one path need be shown in the solution.
 */

public class LowCostPathFinder {

    private static Map<Integer, String> sRowIndex = null;

    private static Map<Integer, String> sResultRowIndex = null;

    private static int sMinCost = Integer.MAX_VALUE;

    private static int sIMinCost = Integer.MAX_VALUE;

    public static boolean sStatus = false;

    /**
     * Method to find the lowest cost path from the supplied 2D matrix.
     *
     * @param aInput 2dMatrix
     * @return Lowest cost
     */
    public static int findShortestPath( int[][] aInput ) {

        return findShortestPath(aInput, null);
    }

    /**
     * Method to find the lowest cost path from the supplied 2D matrix.
     *
     * @param aInput 2dMatrix
     * @param aListener
     * @return Lowest cost
     */

    public static int findShortestPath( int[][] aInput, UIProgressListener aListener ) {

        reset();
        for (int lRow = 0; lRow < aInput.length; lRow++) {

            if (aInput[lRow][0] > CommonUtils.MAX_COST) {
                continue;
            }
            if (null != sRowIndex) {
                sRowIndex.clear();
            }
            findShortestPathInternal(aInput, lRow, 0, 0, aListener);
            // aListener.onProgressUpdate(sResultRowIndex, aInput.length, aInput[0].length);
        }

        // 1. Print Path finding status.
        if (sMinCost > 0 && sMinCost != Integer.MAX_VALUE) {
            sStatus = true;
            System.out.println("Yes");
        }
        // For the negative inputs.
        else if (sMinCost == 0 && sIMinCost == Integer.MAX_VALUE) {
            sStatus = true;
            System.out.println("Yes");
        } else {
            sStatus = false;
            if (sIMinCost != Integer.MAX_VALUE && sMinCost == Integer.MAX_VALUE) {
                sMinCost = sIMinCost;
            } else if (sMinCost == Integer.MAX_VALUE) {
                sMinCost = 0;
            }
            System.out.println("No");
        }

        // 2. Print cost of the resultant path
        if (!sStatus) {
            sMinCost = CommonUtils.getCostOfThePathMap(aInput, sResultRowIndex);
        }
        System.out.println(sMinCost);

        // 3. Print path string
        if (null != sResultRowIndex) {
            System.out.println(getPath());
        } else {
            System.out.println("[]");
        }
        return sMinCost;
    }

    /**
     * Method to iterate through the supplied position of the input matrix based on
     * the preconditions.
     *
     * @param aInput Input 2D Matrix
     * @param aRow Row - Index
     * @param aCol Column - Index
     * @param aCost - Current cost
     * @param aListener - Listener to notify the progress of finding the
     *            shortest&low cost path.
     */
    private static void findShortestPathInternal(
            int[][] aInput,
            int aRow,
            int aCol,
            int aCost,
            UIProgressListener aListener ) {

        // If not valid index, just return cost
        if (!CommonUtils.isValidIndex(aInput, aRow, aCol)) {
            return;
        }

        if (null == sRowIndex) {
            sRowIndex = new LinkedHashMap<>(aInput[0].length);
        }

        int lCurrentValue = aInput[aRow][aCol];
        int lCurrentCost = aCost + lCurrentValue;

        sRowIndex.put(aCol, (aRow + 1) + "--" + (aCol + 1));

        if (CommonUtils.IS_DEBUG_ENABLED) {
            System.out.println(
                    "[ R, C, Cost][" + aRow + "," + aCol + "," + aCost + "]   "
                            + sRowIndex.values().toString() + "]");
        }

        // If the cost greater than threshold
        if (lCurrentCost > CommonUtils.MAX_COST) {

            if (lCurrentCost != lCurrentValue) {
                if (sIMinCost == Integer.MAX_VALUE || lCurrentCost - lCurrentValue > sIMinCost) {
                    sIMinCost = lCurrentCost - lCurrentValue;
                }
            }
            String lRemovedItem = sRowIndex.remove(aCol);
            if (CommonUtils.IS_DEBUG_ENABLED) {
                System.out.println("[Removing][" + lRemovedItem + "]");
            }

            // How depth is the path.?
            int lCurCost = CommonUtils.getCostOfThePathMap(aInput, sRowIndex);
            int lPreCost = CommonUtils.getCostOfThePathMap(aInput, sResultRowIndex);

            int lCurDepth = CommonUtils.getMaxDepthOfThePathMap(sRowIndex);
            int lPreDepth = CommonUtils.getMaxDepthOfThePathMap(sResultRowIndex);

            if (CommonUtils.IS_DEBUG_ENABLED) {
                System.out.println("Cost [C,P][" + lCurCost + "," + lPreCost + "]");
                System.out.println("Depth[C,P][" + lCurDepth + "," + lPreDepth + "]");
            }

            if ((lPreCost == 0 && lPreDepth == 0)
                    || (lPreCost == 0 && lCurCost > 0 && lCurDepth > lPreDepth)
                    || (lCurCost < lPreCost && lCurDepth >= lPreDepth)
                    || (lCurCost < CommonUtils.MAX_COST && lCurDepth > lPreDepth)) {
                sResultRowIndex = new LinkedHashMap<>(sRowIndex);
            }
            if (CommonUtils.IS_DEBUG_ENABLED) {
                System.out.println("[Result]" + sResultRowIndex.values().toString());
            }
            return;
        }

        // If the cost exceeds the already found minimum cost value
        if (lCurrentCost > sMinCost) {
            return;
        }

        // Does it reached last column?
        if (CommonUtils.isLastColumn(aInput, aRow, aCol)) {

            if (sMinCost > lCurrentCost) {
                sMinCost = lCurrentCost;
                if (null != sRowIndex) {
                    sResultRowIndex = new LinkedHashMap<>(sRowIndex);
                } else {
                    sResultRowIndex = new LinkedHashMap<>();
                }
                if (null != aListener) {
                    aListener.onProgressUpdate(sResultRowIndex, aInput.length, aInput[0].length);
                }
            }
            return;
        }

        // 1. Previous row & next column
        findShortestPathInternal(aInput, aRow - 1, aCol + 1, lCurrentCost, aListener);

        // 2. Same row & next column
        findShortestPathInternal(aInput, aRow, aCol + 1, lCurrentCost, aListener);

        // 3.Next row & next column
        findShortestPathInternal(aInput, aRow + 1, aCol + 1, lCurrentCost, aListener);

        // 4.Adjacency from top to bottom
        if (aRow == 0) {
            findShortestPathInternal(aInput, aInput.length - 1, aCol + 1, lCurrentCost, aListener);
        }

        // 5.Adjacency from bottom to top
        if (aRow == aInput.length - 1) {
            findShortestPathInternal(aInput, 0, aCol + 1, lCurrentCost, aListener);
        }
    }

    /**
     * Method to reset the algo State's. Used for testing.
     */
    public static void reset() {
        sStatus = false;
        sMinCost = Integer.MAX_VALUE;
        sIMinCost = Integer.MAX_VALUE;
        sRowIndex = null;
        sResultRowIndex = null;
    }

    /**
     * Method to return the path-indexes in a map
     *
     * @return
     */
    public static Map<Integer, String> getResultIndex() {
        return sResultRowIndex;
    }

    /**
     * Indicate the path made it all the way through the grid.
     *
     * @return True If it made through the grid.
     */
    public static final String getStatus() {
        return sStatus ? "Yes" : "No";
    }

    /**
     * Return the shortest path represented as a sequence of 'n' delimited integers,
     * each representing the rows traversed in turn.
     *
     * @return Path string.
     */
    public static final String getPath() {

        if (null != sResultRowIndex && sResultRowIndex.size() > 0) {

            StringBuilder lBuilder = new StringBuilder("[");

            Collection<String> lList = sResultRowIndex.values();
            int lSize = (null == lList) ? 0 : lList.size();

            List<Integer> lResult = new ArrayList<>();

            for (String lData : lList) {
                lResult.add(Integer.parseInt(lData.split("--")[0]));
            }
            // Collections.sort(lResult);
            int i = 0;
            for (Integer lData : lResult) {
                if (i == lSize - 1) {

                    lBuilder.append("" + lData);
                } else {
                    lBuilder.append(lData + " ");
                }
                i++;
            }
            return lBuilder.append("]").toString();

        } else {
            return "[]";
        }
    }
}
