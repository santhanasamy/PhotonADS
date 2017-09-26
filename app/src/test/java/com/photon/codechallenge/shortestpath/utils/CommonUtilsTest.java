
package com.photon.codechallenge.shortestpath.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the common utilities in the application.
 */
public class CommonUtilsTest {

    @Test
    public void testAcceptedDecimalInput() {

        assertTrue(UiUtils.isValidInput("1234"));
    }

    @Test
    public void testAcceptedSignedDecimalInput() {

        assertTrue(UiUtils.isValidInput("-1234"));
    }

    @Test
    public void testInvalidAlphanumericInput() {

        assertTrue(!UiUtils.isValidInput("-1234Santhana"));
    }

    @Test
    public void testNonDecimalInput() {

        assertTrue(!UiUtils.isValidInput("1234.114"));
    }

    @Test
    public void testLastColumn() {

        assertTrue(
                CommonUtils.isLastColumn(
                        SampleInputs.GENERAL_TEST_CASE_1,
                        0,
                        SampleInputs.GENERAL_TEST_CASE_1[0].length - 1));
    }

    @Test
    public void testInvalidIndex() {

        assertTrue(
                !CommonUtils.isValidIndex(
                        SampleInputs.GENERAL_TEST_CASE_1,
                        SampleInputs.GENERAL_TEST_CASE_1.length,
                        SampleInputs.GENERAL_TEST_CASE_1[0].length));
    }

    @Test
    public void testConvertArrayToList() {

        List<String> lTestList = new ArrayList<>();
        for (int i = 0; i < SampleInputs.GENERAL_TEST_CASE_1.length; i++) {

            for (int j = 0; j < SampleInputs.GENERAL_TEST_CASE_1[i].length; j++) {
                lTestList.add("" + SampleInputs.GENERAL_TEST_CASE_1[i][j]);
            }
        }
        assertEquals(lTestList, CommonUtils.convertArrayToList(SampleInputs.GENERAL_TEST_CASE_1));

    }

    @Test
    public void testConvertArrayToIntList() {

        List<Integer> lTestList = new ArrayList<>();
        for (int i = 0; i < SampleInputs.GENERAL_TEST_CASE_1.length; i++) {

            for (int j = 0; j < SampleInputs.GENERAL_TEST_CASE_1[i].length; j++) {
                lTestList.add(SampleInputs.GENERAL_TEST_CASE_1[i][j]);
            }
        }
        assertEquals(lTestList, CommonUtils.convertArrayToIntList(SampleInputs.GENERAL_TEST_CASE_1));

    }

    @Test
    public void testConvertListToIntArray() {

        List<String> lTestList = new ArrayList<>();
        for (int i = 0; i < SampleInputs.GENERAL_TEST_CASE_1.length; i++) {

            for (int j = 0; j < SampleInputs.GENERAL_TEST_CASE_1[i].length; j++) {
                lTestList.add("" + SampleInputs.GENERAL_TEST_CASE_1[i][j]);
            }
        }
        assertArrayEquals(SampleInputs.GENERAL_TEST_CASE_1, CommonUtils.convertListToIntArray(lTestList,
                SampleInputs.GENERAL_TEST_CASE_1.length, SampleInputs.GENERAL_TEST_CASE_1[0].length));

    }
}
