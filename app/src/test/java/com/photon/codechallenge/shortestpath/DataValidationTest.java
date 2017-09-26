
package com.photon.codechallenge.shortestpath;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the given sample inputs against the set of pre-conditions.
 */
public class DataValidationTest {

    @Test
    public void testMinNoOfRowsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(SampleInputs.MIN_ROW_TEST_1));
    }

    @Test
    public void testMinNoOfColumnsInGrid() {
        assertTrue(CommonUtils.isValidMatrix(SampleInputs.MIN_COLUMN_TEST));
    }

    @Test
    public void testMaxNoOfRowsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(SampleInputs.MAX_ROW_TEST));
    }

    @Test
    public void testMaxNoOfColumnsInGrid() {
        assertTrue(!CommonUtils.isValidMatrix(SampleInputs.MAX_COLUMN_TEST));
    }

    @Test
    public void testMaxCostThreshold() {
        assertNotEquals(
                "The told cost is not with in the acceptable limit",
                CommonUtils.MAX_COST,
                CommonUtils.findCost(SampleInputs.MAX_COST_THRESHOLD_TEST));
    }

    @Test
    public void testMaxCostThresholdWithAcceptable() {
        assertEquals(
                "The told cost is with in the acceptable limit",
                CommonUtils.MAX_COST,
                CommonUtils.findCost(SampleInputs.MAX_COST_THRESHOLD_ACCEPTABLE_TEST));
    }

    @Test
    public void testValidInput() {
        assertTrue(CommonUtils.isValidMatrix(SampleInputs.TEST_CASE_1));
    }

}
