
package com.photon.codechallenge.shortestpath.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 * 1. The first line is either “Yes” or “No”, to indicate the path made it all
 * the way through the grid <br/>
 * 2. The second line is the total cost. <br/>
 * 3. The third line shows the path taken as a sequence of 'n' delimited
 * integers, each representing the rows traversed in turn. If there is more than
 * one path of least cost, only one path need be shown in the solution.
 */
public class CommonUtils {

    public static final int CHOSEN_PATH_INDICATOR = 999;

    public final static int UI_MAX_ROW = 10;

    public final static int UI_MAX_COLUMN = 10;

    public final static int MIN_NO_OF_ROWS = 1;

    public final static int MIN_NO_OF_COLUMNS = 5;

    public final static int MAX_NO_OF_ROWS = 10;

    public final static int MAX_NO_OF_COLUMNS = 100;

    public final static int MAX_COST = 50;

    private static int sMinCost = Integer.MAX_VALUE;

    private static int sIMinCost = Integer.MAX_VALUE;

    private static Map<Integer, String> sRowIndex = null;

    private static Map<Integer, String> sResultRowIndex = null;

    public static boolean sStatus = false;

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

    /**
     * Method to validate the supplied matrix against the pre & boundary conditions
     *
     * @param aInput 2D Matrix
     * @return True If the matrix satisfied all the pre & boundary conditions. False
     *         other wise.
     */
    public static boolean isValidMatrix( int[][] aInput ) {

        if (null == aInput) {
            return false;
        }

        // 1. Ensure no of rows
        if (aInput.length == 0 || aInput.length < MIN_NO_OF_ROWS
                || aInput.length >= MAX_NO_OF_ROWS) {
            return false;
        }

        // 2. Ensure no of columns
        for (int i = 0; i < aInput.length; i++) {

            if (aInput[i].length < MIN_NO_OF_COLUMNS || aInput[i].length >= MAX_NO_OF_COLUMNS) {
                return false;
            }
        }

        // 3. Non numeric input
        // for (int i = 0; i < aInput.length; i++) {
        //
        // for (int j = 0; j < aInput[i].length; j++) {
        //
        // try {
        // Double.parseDouble(str);
        // } catch (NumberFormatException nfe) {
        // return false;
        // }
        // }
        // }
        return true;
    }

    /**
     * Method to find the total cost of the 2D-Matrix
     *
     * @param aInputMatrix
     * @return
     */
    public static int findCost( int[][] aInputMatrix ) {

        int lSum = 0;
        for (int i = 0; i < aInputMatrix.length; i++) {
            for (int j = 0; j < aInputMatrix[i].length; j++) {
                lSum = lSum + aInputMatrix[i][j];
            }
        }
        return lSum;
    }

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

            if (aInput[lRow][0] > MAX_COST) {
                continue;
            }
            if (null != sRowIndex) {
                sRowIndex.clear();
            }
            findShortestPathInternal(aInput, lRow, 0, 0, aListener);
            // aListener.onProgressUpdate(sResultRowIndex, aInput.length, aInput[0].length);
        }

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
        System.out.println(sMinCost);
        if (null != sResultRowIndex) {
            System.out.println(getPath());
        } else {
            System.out.println("[]");
        }
        return sMinCost;
    }

    private final static boolean isDebug = false;

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
        if (!isValidIndex(aInput, aRow, aCol)) {
            return;
        }

        if (null == sRowIndex) {
            sRowIndex = new LinkedHashMap<>(aInput[0].length);
        }

        int lCurrentValue = aInput[aRow][aCol];
        int lCurrentCost = aCost + lCurrentValue;

        if (isDebug) {
            System.out.println(
                    "[ R, C, Cost][" + aRow + "," + aCol + "," + aCost + " -- "
                            + sRowIndex.values().toString() + "]");
        }

        sRowIndex.put(aCol, (aRow + 1) + "--" + (aCol + 1));

        // If the cost greater than threshold
        if (lCurrentCost > MAX_COST) {

            if (lCurrentCost != lCurrentValue) {
                if (sIMinCost == Integer.MAX_VALUE || lCurrentCost - lCurrentValue > sIMinCost) {
                    sIMinCost = lCurrentCost - lCurrentValue;
                }
            }
            sRowIndex.remove(aCol);

            // How depth is the path.?
            int lCurCost = findCostOfThePathMap(aInput, sRowIndex);
            int lPreCost = findCostOfThePathMap(aInput, sResultRowIndex);
            if (isDebug) {
                System.out.println("[C,P][" + lCurCost + "," + lPreCost + "]");
            }
            if (lCurCost > lPreCost) {
                sResultRowIndex = new LinkedHashMap<>(sRowIndex);
            }
            return;
        }

        // If the cost exceeds the already found minimum cost value
        if (lCurrentCost > sMinCost) {
            return;
        }

        // Does it reached last column?
        if (isLastColumn(aInput, aRow, aCol)) {

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
     * Method to find the cost of the path supplied as map.
     * 
     * @param aInput 2D-Input matrix
     * @param sRowIndex Selected path index's
     * @return Cost
     */
    private static int findCostOfThePathMap( int[][] aInput, Map<Integer, String> sRowIndex ) {

        int lRes = 0;
        if (null == sRowIndex || null == sRowIndex.values() || sRowIndex.values().size() == 0) {
            return lRes;
        }
        for (String aStr : sRowIndex.values()) {

            String[] lArray = aStr.split("--");
            int lRow = Integer.parseInt(lArray[0]);
            int lCol = Integer.parseInt(lArray[1]);
            lRes += aInput[lRow - 1][lCol - 1];
        }
        return lRes;
    }

    /**
     * Method to reset the algo State's. Used for testing.
     */
    private static void reset() {
        sStatus = false;
        sMinCost = Integer.MAX_VALUE;
        sIMinCost = Integer.MAX_VALUE;
        sRowIndex = null;
        sResultRowIndex = null;
    }

    /**
     * Method to check the supplied ROW & COLUMN indexes are pointing the last
     * column of the matrix
     *
     * @param aInput
     * @param aRow
     * @param aCol
     * @return True If it the indexes are pointing the last column of the matrix
     */
    public static final boolean isLastColumn( int[][] aInput, int aRow, int aCol ) {
        return aCol == aInput[aRow].length - 1;
    }

    /**
     * Method to validate whether the supplied ROW & COLUMN indexes are valid
     *
     * @param aInput
     * @param aRow
     * @param aCol
     * @return True If the indexes are valid
     */
    public static final boolean isValidIndex( int[][] aInput, int aRow, int aCol ) {
        return (aRow >= 0 && aRow < aInput.length && aCol >= 0 && aCol < aInput[aRow].length);
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
     * Method to return the path-indexes in a map
     * 
     * @return
     */
    public static Map<Integer, String> getResultIndex() {
        return sResultRowIndex;
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

    /**
     * Method to convert 2D array to List<String>
     *
     * @param aData
     * @return List
     */
    public static List<String> convertArrayToList( int[][] aData ) {
        List<String> lList = new ArrayList<>();
        for (int i = 0; i < aData.length; i++) {
            for (int j = 0; j < aData[i].length; j++) {
                lList.add("" + aData[i][j]);
            }
        }
        return lList;
    }

    /**
     * Method to convert 2D array to List<Integer>
     *
     * @param aData
     * @return
     */
    public static List<Integer> convertArrayToIntList( int[][] aData ) {
        List<Integer> lList = new ArrayList<>();
        for (int i = 0; i < aData.length; i++) {
            for (int j = 0; j < aData[i].length; j++) {
                lList.add(aData[i][j]);
            }
        }
        return lList;
    }

    /**
     * Method to convert 2D array to List<Integer>
     *
     * @param aList
     * @param aRows
     * @param aColumns
     * @return
     */
    public static int[][] convertListToIntArray( List<String> aList, int aRows, int aColumns ) {

        int[][] lData = new int[aRows][aColumns];

        int lListIdx = 0;
        for (int lRow = 0; lRow < aRows; lRow++) {
            for (int lColumn = 0; lColumn < aColumns; lColumn++) {

                if (lListIdx >= aList.size()) {
                    continue;
                }
                String lCost = aList.get(lListIdx);
                if (TextUtils.isEmpty(lCost)) {
                    lData[lRow][lColumn] = 0;
                } else {
                    lData[lRow][lColumn] = Integer.parseInt(lCost);
                }
                lListIdx = lListIdx + 1;
            }
        }
        return lData;
    }
}