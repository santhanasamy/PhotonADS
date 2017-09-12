package com.photon.codechallenge.shortestpath.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/> 1. The first line is either “Yes” or “No”, to indicate the path made it all the way through the grid
 * <br/> 2. The second line is the total cost.
 * <br/> 3. The third line shows the path taken as a sequence of 'n' delimited integers,
 * each representing the rows traversed in turn. If there is more than one path of least cost,
 * only one path need be shown in the solution.
 */
public class CommonUtils {

    public final static int UI_MAX_ROWS = 10;
    public final static int UI_MAX_COLUMNS = 10;

    public final static int MIN_NO_OF_ROWS = 1;
    public final static int MIN_NO_OF_COLUMNS = 5;

    public final static int MAX_NO_OF_ROWS = 10;
    public final static int MAX_NO_OF_COLUMNS = 100;

    public final static int MAX_COST = 50;

    private static int sMinCost = Integer.MAX_VALUE;

    private static int sIMinCost = Integer.MAX_VALUE;

    private static Map<Integer, Integer> sRowIndex = null;

    private static Map<Integer, Integer> sResultRowIndex = null;

    public static boolean isValidMatrix(int[][] aInput) {

        if (null == aInput) {
            return false;
        }

        // 1. Ensure  no of rows
        if (aInput.length == 0 || aInput.length < MIN_NO_OF_ROWS || aInput.length >= MAX_NO_OF_ROWS) {
            return false;
        }


        // 2. Ensure no of columns
        for (int i = 0; i < aInput.length; i++) {

            if (aInput[i].length < MIN_NO_OF_COLUMNS || aInput[i].length >= MAX_NO_OF_COLUMNS) {
                return false;
            }
        }

        //3. Non numeric input
//        for (int i = 0; i < aInput.length; i++) {
//
//            for (int j = 0; j < aInput[i].length; j++) {
//
//                try {
//                    Double.parseDouble(str);
//                } catch (NumberFormatException nfe) {
//                    return false;
//                }
//            }
//        }
        return true;
    }

    public static int findCost(int[][] aInputMatrix) {

        int lSum = 0;
        for (int i = 0; i < aInputMatrix.length; i++) {
            for (int j = 0; j < aInputMatrix[i].length; j++) {
                lSum = lSum + aInputMatrix[i][j];
            }
        }
        return lSum;
    }

    public static int findShortestPath(int[][] aInput) {

        reset();
        for (int lRow = 0; lRow < aInput.length; lRow++) {

            if (aInput[lRow][0] > MAX_COST) {
                continue;
            }
            findShortestPath(aInput, lRow, 0, 0);
            sRowIndex.clear();
        }

        if (sMinCost > 0 && sMinCost != Integer.MAX_VALUE) {
            System.out.println("Yes");
        } else {
            if (sIMinCost != Integer.MAX_VALUE && sMinCost == Integer.MAX_VALUE) {
                sMinCost = sIMinCost;
            } else if (sMinCost == Integer.MAX_VALUE) {
                sMinCost = 0;
            }
            System.out.println("No");
        }
        System.out.println(sMinCost);
        System.out.println(sResultRowIndex.values().toString());
        return sMinCost;
    }

    /**
     * Method to reset the state of algo.
     * Used for testing.
     */
    private static void reset() {
        sMinCost = Integer.MAX_VALUE;
        sIMinCost = Integer.MAX_VALUE;
        sRowIndex = null;
    }

    private static void findShortestPath(int[][] aInput,
                                         int aRow, int aCol, int aCost) {

        //If not valid index, just return cost
        if (!isValidIndex(aInput, aRow, aCol)) {
            return;
        }

        if (null == sRowIndex) {
            sRowIndex = new HashMap<>(aInput[0].length);
        }

        sRowIndex.put(aCol, aRow + 1);

        int lCurrentValue = aInput[aRow][aCol];
        int lCurrentCost = aCost + lCurrentValue;
        //System.out.println("[ R, C, Cost][" + aRow + "," + aCol + "," + aCost + "]");

        // If the cost greater than threshold
        if (lCurrentCost > MAX_COST) {

            if (lCurrentCost != lCurrentValue) {
                if (sIMinCost == Integer.MAX_VALUE || lCurrentCost - lCurrentValue > sIMinCost) {
                    sIMinCost = lCurrentCost - lCurrentValue;
                }
            }
            return;
        }

        //If the cost exceeds the already found minimum cost value
        if (lCurrentCost > sMinCost) {
            return;
        }

        //Does it reached last column
        if (isLastColumn(aInput, aRow, aCol)) {

            if (sMinCost > lCurrentCost) {
                sMinCost = lCurrentCost;
                sResultRowIndex = new HashMap<>(sRowIndex);
                //System.out.println(sResultRowIndex.values().toString() + "[Cost][" + sMinCost + "]");
            }
            return;
        }


        //1. Previous row & next column
        findShortestPath(aInput, aRow - 1, aCol + 1, lCurrentCost);

        //2. Same row & next column
        findShortestPath(aInput, aRow, aCol + 1, lCurrentCost);

        //3.Next row & next column
        findShortestPath(aInput, aRow + 1, aCol + 1, lCurrentCost);

        //4.Adjacency from top to bottom
        if (aRow == 0) {
            findShortestPath(aInput, aInput.length - 1, aCol + 1, lCurrentCost);
        }

        //5.Adjacency from bottom to top
        if (aRow == aInput.length - 1) {
            findShortestPath(aInput, 0, aCol + 1, lCurrentCost);
        }
    }

    public static final boolean isLastColumn(int[][] aInput, int aRow, int aCol) {
        return aCol == aInput[aRow].length - 1;
    }

    public static final boolean isValidIndex(int[][] aInput, int aRow, int aCol) {
        return (aRow >= 0 && aRow < aInput.length && aCol >= 0 && aCol < aInput[aRow].length);
    }
}