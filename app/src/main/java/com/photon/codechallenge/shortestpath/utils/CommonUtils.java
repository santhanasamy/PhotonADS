
package com.photon.codechallenge.shortestpath.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to keep common utils
 */
public class CommonUtils {

    public final static boolean IS_DEBUG_ENABLED = false;

    public static final int CHOSEN_PATH_INDICATOR = 999;

    public final static int UI_MAX_ROW = 10;

    public final static int UI_MAX_COLUMN = 10;

    public final static int MIN_NO_OF_ROWS = 1;

    public final static int MIN_NO_OF_COLUMNS = 5;

    public final static int MAX_NO_OF_ROWS = 10;

    public final static int MAX_NO_OF_COLUMNS = 100;

    public final static int MAX_COST = 50;

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
     * Method to find the cost of the path supplied as map.
     *
     * @param aInput 2D-Input matrix
     * @param sRowIndex Selected path index's
     * @return Cost
     */
    public static int getCostOfThePathMap( int[][] aInput, Map<Integer, String> sRowIndex ) {

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
     * Method to find the maximum depth (column wise)of the path supplied as map.
     *
     * @param sRowIndex Selected path index's
     * @return Max Depth of the path
     */
    public static int getMaxDepthOfThePathMap( Map<Integer, String> sRowIndex ) {

        int lRes = 0;
        if (null == sRowIndex || null == sRowIndex.values() || sRowIndex.values().size() == 0) {
            return lRes;
        }

        for (String aStr : sRowIndex.values()) {

            String[] lArray = aStr.split("--");
            int lCol = Integer.parseInt(lArray[1]);

            if (lRes < lCol) {
                lRes = lCol;
            }
        }
        return lRes;
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
     * Method to convert 2D array to List<String>
     *
     * @param aData 2D Input Matrix
     * @return List Flattened 2D matrix as a List of String
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
     * @param aData 2D Input Matrix
     * @return Flattened 2D matrix as a List of Integer
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
     * @param aList List of data
     * @param aRows No of rows
     * @param aColumns No of columns
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
