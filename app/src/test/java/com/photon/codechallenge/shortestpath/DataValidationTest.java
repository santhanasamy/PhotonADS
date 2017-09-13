
package com.photon.codechallenge.shortestpath;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the given sample inputs against the set of pre-conditions.
 */
public class DataValidationTest {

    private static final int[][] MIN_ROW_TEST_1 = new int[][] {};

    private static final int[][] MIN_COLUMN_TEST = new int[][] {
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
            }
    };

    private static final int[][] MAX_ROW_TEST = new int[][] {
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
            }, {
                    6, 1, 8, 2, 7, 4
            }, {
                    5, 9, 3, 9, 9, 5
            }, {
                    8, 4, 1, 3, 2, 6
            }, {
                    3, 7, 2, 8, 6, 4
            }, {
                    8, 4, 1, 3, 2, 6
            }, {
                    3, 7, 2, 8, 6, 4
            }
    };

    private static final int[][] MAX_COLUMN_TEST = new int[][] {
            {
                    3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 8, 9, 9, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32,
                    2, 8, 9, 9, 2, 3, 4, 5, 8, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 8, 9, 9, 6,
                    7, 2, 8, 9, 9, 2, 3, 4, 5, 8, 6, 7, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 8,
                    9, 9, 4, 5, 8, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 3, 4, 4, 6, 7, 8, 9, 3,
                    4, 5, 6, 32, 2, 8, 9, 9, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 8, 9, 9, 4, 5,
                    8, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32, 2, 3, 4, 4, 6, 7, 8, 9, 3, 4, 5, 6, 32,
                    2, 8, 9, 9, 11
            }
    };

    private static final int[][] MAX_COST_THRESHOLD_TEST = new int[][] {
            {
                    10, 10, 10, 10, 10, 1
            }
    };

    private static final int[][] MAX_COST_THRESHOLD_ACCEPTABLE_TEST = new int[][] {
            {
                    10, 10, 10, 10, 10
            }
    };

    @Test
    public void testMinNoOfRowsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(MIN_ROW_TEST_1));
    }

    @Test
    public void testMinNoOfColumnsInGrid() {
        assertTrue(CommonUtils.isValidMatrix(MIN_COLUMN_TEST));
    }

    @Test
    public void testMaxNoOfRowsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(MAX_ROW_TEST));
    }

    @Test
    public void testMaxNoOfColumnsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(MAX_COLUMN_TEST));
    }

    @Test
    public void testMaxCostThreshold() {
        assertNotEquals(
                "The told cost is not with in the acceptable limit",
                CommonUtils.MAX_COST,
                CommonUtils.findCost(MAX_COST_THRESHOLD_TEST));
    }

    @Test
    public void testMaxCostThresholdWithAcceptable() {
        assertEquals(
                "The told cost is with in the acceptable limit",
                CommonUtils.MAX_COST,
                CommonUtils.findCost(MAX_COST_THRESHOLD_ACCEPTABLE_TEST));
    }

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

    private static final int[][] TEST_CASE_2 = new int[][] {

            {
                    3, 4, 1, 2, 8, 6
            }, {
                    6, 1, 8, 2, 7, 4
            }, {
                    5, 9, 3, 9, 9, 5
            }, {
                    8, 4, 1, 3, 2, 6
            }, {
                    3, 7, 2, 1, 2, 3
            }
    };

    private static final int[][] TEST_CASE_3 = new int[][] {
            {
                    19, 10, 19, 10, 19
            }, {
                    21, 23, 20, 19, 12
            }, {
                    20, 12, 20, 11, 10
            }
    };

    private static final int[][] TEST_CASE_4 = new int[][] {
            {
                    5, 8, 5, 3, 5
            }
    };

    private static final int[][] TEST_CASE_5 = new int[][] {
            {
                    5
            }, {
                    8
            }, {
                    5
            }, {
                    3
            }, {
                    5
            }
    };

    // private static final int[][] TEST_CASE_6 = new int[][]{
    // {5, 4, H},
    // {8, M, 7},
    // {5, 7, 5}
    // };

    private static final int[][] TEST_CASE_7 = new int[][] {

    };

    private static final int[][] TEST_CASE_8 = new int[][] {
            {
                    69, 10, 19, 10, 19
            }, {
                    51, 23, 20, 19, 12
            }, {
                    60, 12, 20, 11, 10
            }
    };

    private static final int[][] TEST_CASE_9 = new int[][] {
            {
                    60, 3, 3, 6
            }, {
                    6, 3, 7, 9
            }, {
                    5, 6, 8, 3
            }
    };

    private static final int[][] TEST_CASE_10 = new int[][] {
            {
                    6, 3, -5, 9
            }, {
                    -5, 2, 4, 10
            }, {
                    3, -2, 6, 10
            }, {
                    6, -1, -2, 10
            }
    };

    private static final int[][] TEST_CASE_11 = new int[][] {

            {
                    51, 51
            }, {
                    0, 51
            }, {
                    51, 51
            }, {
                    5, 5
            }
    };

    private static final int[][] TEST_CASE_12 = new int[][] {

            {
                    51, 51, 51
            }, {
                    0, 51, 51
            }, {
                    51, 51, 51
            }, {
                    5, 5, 51
            }
    };

    private static final int[][] TEST_CASE_13 = new int[][] {
            {
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
            }, {
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2
            }
    };

    @Test
    public void testValidInput() {
        assertTrue(CommonUtils.isValidMatrix(TEST_CASE_1));
    }

    @Test
    public void test6X5NormalMatrix() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_1) == 16);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[1 2 3 4 4 5]");
    }

    @Test
    public void test6X5NormalMatrix1() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_2) == 11);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[1 2 1 5 4 5]");
    }

    @Test
    /**
     * 5X3 matrix with no path <50
     */
    public void test5X3MatrixWithNoValidPath() {
        // assertTrue(CommonUtils.findShortestPathInternal(TEST_CASE_3) == 48);
    }

    @Test
    public void test1X5NormalMatrix() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_4) == 26);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[1 1 1 1 1]");
    }

    @Test
    public void test5X1NormalMatrix() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_5) == 3);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[4]");
    }

    /**
     * Non numeric input, Optional
     */
    @Test
    public void testNonNumeric() {
        // assertTrue(CommonUtils.findShortestPathInternal(TEST_CASE_6) == 11);
    }

    @Test
    public void testNoInputMatrix() {
        // assertTrue("Empty Input", !CommonUtils.isValidInput(TEST_CASE_7));
    }

    @Test
    /**
     * Starting with >50
     */
    public void testMatrixWithStartingGraterThanThreshold() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_8) == 0);
        assertEquals(CommonUtils.getStatus(), "No");
        assertEquals(CommonUtils.getPath(), "[]");
    }

    @Test
    public void testMatrixWithOneValueGraterThanThreshold() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_9) == 14);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[3 2 1 3]");
    }

    @Test
    public void testMatrixWithNegativeValues() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_10) == 0);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[2 3 4 1]");
    }

    @Test
    /**
     * Complete path vs. lower cost incomplete path
     */
    public void testMatrixWithCompletePathLowerCost() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_11) == 10);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[4 4]");
    }

    @Test
    /**
     * Longer incomplete path vs. shorter lower cost incomplete path
     */
    public void testMatrixWithLongerIncompletePathShorterLowerCost() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_12) == 10);
        assertEquals(CommonUtils.getStatus(), "No");
        assertEquals(CommonUtils.getPath(), "[4 4]");
    }

    @Test
    public void testMatrixWithLargeNoOfColumns() {
        assertTrue(CommonUtils.findShortestPath(TEST_CASE_13) == 20);
        assertEquals(CommonUtils.getStatus(), "Yes");
        assertEquals(CommonUtils.getPath(), "[1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1]");

    }
}
