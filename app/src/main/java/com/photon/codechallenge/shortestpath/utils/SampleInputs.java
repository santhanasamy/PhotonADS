
package com.photon.codechallenge.shortestpath.utils;

/**
 * Class to hold sample inputs
 */

public class SampleInputs {

    public static final int[][] MIN_COLUMN_TEST = new int[][] {
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

    public static final int[][] MAX_ROW_TEST = new int[][] {
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

    public static final int[][] MIN_ROW_TEST_1 = new int[][] {};

    public static final int[][] MAX_COLUMN_TEST = new int[][] {
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

    public static final int[][] MAX_COST_THRESHOLD_TEST = new int[][] {
            {
                    10, 10, 10, 10, 10, 1
            }
    };

    public static final int[][] MAX_COST_THRESHOLD_ACCEPTABLE_TEST = new int[][] {
            {
                    10, 10, 10, 10, 10
            }
    };

    public static final int[][] TEST_CASE_1 = new int[][] {
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

    public static final int[][] TEST_CASE_2 = new int[][] {

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

    public static final int[][] TEST_CASE_3 = new int[][] {
            {
                    19, 10, 19, 10, 19
            }, {
                    21, 23, 20, 19, 12
            }, {
                    20, 12, 20, 11, 10
            }
    };

    public static final int[][] TEST_CASE_4 = new int[][] {
            {
                    5, 8, 5, 3, 5
            }
    };

    public static final int[][] TEST_CASE_5 = new int[][] {
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

    // public static final int[][] TEST_CASE_6 = new int[][]{
    // {5, 4, H},
    // {8, M, 7},
    // {5, 7, 5}
    // };

    public static final int[][] TEST_CASE_7 = new int[][] {

    };

    public static final int[][] TEST_CASE_8 = new int[][] {
            {
                    69, 10, 19, 10, 19
            }, {
                    51, 23, 20, 19, 12
            }, {
                    60, 12, 20, 11, 10
            }
    };

    public static final int[][] TEST_CASE_9 = new int[][] {
            {
                    60, 3, 3, 6
            }, {
                    6, 3, 7, 9
            }, {
                    5, 6, 8, 3
            }
    };

    public static final int[][] TEST_CASE_10 = new int[][] {
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

    public static final int[][] TEST_CASE_11 = new int[][] {

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

    public static final int[][] TEST_CASE_12 = new int[][] {

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

    public static final int[][] TEST_CASE_13 = new int[][] {
            {
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
            }, {
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2
            }
    };

    public static final int[][] TEST_CASE_14 = new int[][] {
            {
                    19, 10, 19, 10, 19
            }, {
                    21, 23, 20, 19, 12
            }, {
                    20, 12, 20, 11, 10
            }
    };

    public static final int[][] TEST_CASE_15 = new int[][] {
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

    public static final int[][] TEST_CASE_100 = new int[][] {
            {
                    51, 51, 51
            }, {
                    00, 51, 10
            }, {
                    51, 51, 50
            }, {
                    05, 05, 50
            }
    };
}
