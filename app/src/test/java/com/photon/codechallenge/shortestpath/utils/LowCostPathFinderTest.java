
package com.photon.codechallenge.shortestpath.utils;

import com.photon.codechallenge.shortestpath.utils.LowCostPathFinder;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests to verify the correctness of the lowest cost path algorithm.
 */

public class LowCostPathFinderTest {

    @Test
    public void test6X5NormalMatrix() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_1) == 16);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 2 3 4 4 5]");
    }

    @Test
    public void test6X5NormalMatrix1() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_2) == 11);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 2 1 5 4 5]");
    }

    @Test
    /**
     * 5X3 matrix with no path <50
     */
    public void test5X3MatrixWithNoValidPath() {
        // assertTrue(CommonUtils.findShortestPathInternal(SampleInputs.TEST_CASE_3) ==
        // 48);
    }

    @Test
    public void test1X5NormalMatrix() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_4) == 26);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 1 1 1 1]");
    }

    @Test
    public void test5X1NormalMatrix() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_5) == 3);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[4]");
    }

    /**
     * Non numeric input, Optional
     */
    @Test
    public void testNonNumeric() {
        // assertTrue(CommonUtils.findShortestPathInternal(SampleInputs.TEST_CASE_6) ==
        // 11);
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
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_8) == 0);
        assertEquals(LowCostPathFinder.getStatus(), "No");
        assertEquals(LowCostPathFinder.getResultantPath(), "[]");
    }

    @Test
    public void testMatrixWithOneValueGraterThanThreshold() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_9) == 14);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[3 2 1 3]");
    }

    @Test
    public void testMatrixWithNegativeValues() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_10) == 0);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[2 3 4 1]");
    }

    @Test
    /**
     * Complete path vs. lower cost incomplete path
     */
    public void testMatrixWithCompletePathLowerCost() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_11) == 10);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[4 4]");
    }

    @Test
    /**
     * Longer incomplete path vs. shorter lower cost incomplete path
     */
    public void testMatrixWithLongerIncompletePathShorterLowerCost() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_12) == 10);
        assertEquals(LowCostPathFinder.getStatus(), "No");
        assertEquals(LowCostPathFinder.getResultantPath(), "[4 4]");
    }

    @Test
    public void testMatrixWithLargeNoOfColumns() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_13) == 20);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1]");

    }

    @Test
    public void testMatrixWith3X5() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_14) == 48);
        assertEquals(LowCostPathFinder.getStatus(), "No");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 1 1]");
    }

    @Test
    public void testMatrixWith5X6() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.TEST_CASE_15) == 11);
        assertEquals(LowCostPathFinder.getStatus(), "Yes");
        assertEquals(LowCostPathFinder.getResultantPath(), "[1 2 1 5 4 5]");
    }

    @Test
    public void testCommonCases() {
        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.GENERAL_TEST_CASE_1) == 10);
        assertEquals(LowCostPathFinder.getStatus(), "No");
        assertEquals(LowCostPathFinder.getResultantPath(), "[4 4]");

        assertTrue(LowCostPathFinder.findShortestPath(SampleInputs.GENERAL_TEST_CASE_2) == 48);
        assertEquals(LowCostPathFinder.getStatus(), "No");
        assertEquals(LowCostPathFinder.getResultantPath(), "[2 2 2]");

    }
}
