
package com.photon.codechallenge.shortestpath;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.photon.codechallenge.shortestpath.ui.InputCollectorActivity;
import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Tests to check the InputCollector Activity & PathFinderActivity.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LowCostPathTest {

    public static final int mLowerBound = 0;

    public static final int mUpperBound = 50;

    private static final int TEST_ACTION_DELAY = 1000;

    private static final int TEST_EXE_DELAY = 2000;

    @Rule
    public ActivityTestRule<InputCollectorActivity> mActivityRule = new ActivityTestRule(
            InputCollectorActivity.class);

    @Test
    /**
     * Test user entered row values are entered properly
     */
    public void testRowInput() {

        String lRows = "" + CommonUtils.UI_MAX_ROW;
        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRows), closeSoftKeyboard());
        onView(withId(R.id.rows_edit_txt_view)).check(matches(withText(lRows)));
    }

    @Test
    /**
     * Test whether the user entered row value is reset to
     * {@link CommonUtils.UI_MAX_ROW}, If the value is greater than the allowed
     * maximum no of rows {@link CommonUtils.UI_MAX_ROW}
     */
    public void testResettingMaxAllowedRowInput() {

        String lAllowedNoOfRows = "" + CommonUtils.UI_MAX_ROW;
        String lEnteredRows = "" + (CommonUtils.UI_MAX_ROW + 1);

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText(lEnteredRows), closeSoftKeyboard());
        onView(withId(R.id.rows_edit_txt_view)).check(matches(withText(lAllowedNoOfRows)));
    }

    /**
     * Test user entered column values are entered properly
     */
    @Test
    public void testColumnInput() {
        String lColumns = "" + CommonUtils.UI_MAX_COLUMN;
        onView(withId(R.id.columns_edit_txt_view)).perform(typeText(lColumns), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view)).check(matches(withText(lColumns)));
    }

    @Test
    /**
     * Test whether the user entered column value is reset to
     * {@link CommonUtils.UI_MAX_COLUMN}, If the value is greater than the allowed
     * maximum no of columns {@link CommonUtils.UI_MAX_COLUMN}
     */
    public void testResettingMaxAllowedColumnInput() {

        String lAllowedNoOfColumn = "" + CommonUtils.UI_MAX_COLUMN;
        String lEnteredColumn = "" + (CommonUtils.UI_MAX_COLUMN + 1);

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText(lEnteredColumn), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view)).check(matches(withText(lAllowedNoOfColumn)));
    }

    @Test
    /**
     * Test the behaviour & state of calculate button in the user input screen. It
     * should be in the disabled state If the user changed row or column values
     * entered previously.
     */
    public void testCalculateButtonIsDisabled() {

        String lRows = "" + CommonUtils.UI_MAX_ROW;
        String lColumn = "" + CommonUtils.UI_MAX_COLUMN;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRows), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view)).perform(typeText(lColumn), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(1000);
        lRows = "" + (CommonUtils.UI_MAX_ROW - 1);
        onView(withId(R.id.rows_edit_txt_view)).perform(clearText()).perform(click()).perform(
                typeText(lRows),
                closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.calculate_btn)).check(matches(not(isEnabled())));
        SystemClock.sleep(TEST_EXE_DELAY);
    }

    @Test
    /**
     * Test the behaviour & state of calculate button in the user input screen. It
     * should be in the disabled state If the user changed row or columns values
     * entered previously. And It should be enabled after tapping on the generate
     * button.
     */
    public void testCalculateButtonIsEnabled() {

        String lRows = "" + CommonUtils.UI_MAX_ROW;
        String lColumn = "" + CommonUtils.UI_MAX_COLUMN;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRows), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view)).perform(typeText(lColumn), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);
        lRows = "" + (CommonUtils.UI_MAX_ROW - 1);
        onView(withId(R.id.rows_edit_txt_view)).perform(clearText()).perform(click()).perform(
                typeText(lRows),
                closeSoftKeyboard());
        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.calculate_btn)).check(matches(not(isEnabled())));

        onView(withId(R.id.generate_row_btn)).perform(click());

        onView(withId(R.id.calculate_btn)).check(matches(isEnabled()));

        SystemClock.sleep(TEST_EXE_DELAY);
    }

    @Test
    /**
     * Test no of rows generated through the generate button. No of rows generated
     * should be equal to the user enter value of no of rows.
     */
    public void testNoOfRowsGenerated() {

        int lNoOfRows = Utils.getRandomColumnCountWithInBound();
        int lNoOfColumns = Utils.getRandomRowCountWithInBound();
        String lRowStr = "" + lNoOfRows;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRowStr), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);

        onView(withId(R.id.input_container))
                .check(matches(Utils.withChildViewCount(lNoOfRows, withId(R.id.input_container))));

        SystemClock.sleep(TEST_EXE_DELAY);
    }

    @Test
    public void testWithRandomUserInputs() {

        int lNoOfRows = Utils.getRandomColumnCountWithInBound();
        int lNoOfColumns = Utils.getRandomRowCountWithInBound();

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + lNoOfRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());

        for (int i = 0; i < lNoOfRows; i++) {
            StringBuilder lBuilder = new StringBuilder();
            for (int j = 0; j < lNoOfColumns; j++) {
                int lRandomNo = (int) (Math.random() * ((mUpperBound - mLowerBound) + 1)
                        + mLowerBound);
                if (j == lNoOfColumns - 1) {
                    lBuilder.append(lRandomNo);
                } else {
                    lBuilder.append(lRandomNo).append(",");
                }
            }
            SystemClock.sleep(TEST_ACTION_DELAY);
            onView(withId(i))
                    .perform(scrollTo(), typeText(lBuilder.toString()), closeSoftKeyboard());
        }
        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.calculate_btn)).perform(click());

        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        SystemClock.sleep(TEST_EXE_DELAY);
    }

    /**
     * Test the behaviour of the clear button.
     */
    @Test
    public void testClearButton() {

        int lNoOfRows = Utils.getRandomColumnCountWithInBound();
        int lNoOfColumns = Utils.getRandomRowCountWithInBound();

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + lNoOfRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);

        onView(withId(R.id.clear_btn)).perform(click());

        onView(withId(R.id.columns_edit_txt_view)).check(matches(withText("")));
        onView(withId(R.id.columns_edit_txt_view)).check(matches(withText("")));

        onView(withId(R.id.input_container))
                .check(matches(Utils.withChildViewCount(0, withId(R.id.input_container))));

    }

    @Test
    public void test6X5NormalMatrix() {
        testLowCostPath(SampleInputs.TEST_CASE_1, "16", "Yes", "[1 2 3 4 4 5]");
    }

    @Test
    public void test6X5NormalMatrixWithAdjacency() {
        testLowCostPath(SampleInputs.TEST_CASE_2, "11", "Yes", "[1 2 1 5 4 5]");
    }

    @Test
    public void test5X3MatrixWithNoValidPath() {
        testLowCostPath(SampleInputs.TEST_CASE_3, "48", "No", "[1 1 1]");
    }

    @Test
    public void test1X5NormalMatrix() {
        testLowCostPath(SampleInputs.TEST_CASE_4, "26", "Yes", "[1 1 1 1 1]");
    }

    @Test
    public void test5X1NormalMatrix() {
        testLowCostPath(SampleInputs.TEST_CASE_5, "3", "Yes", "[4]");
    }

    @Test
    public void testMatrixWithStartingGraterThanThreshold() {
        testLowCostPath(SampleInputs.TEST_CASE_8, "0", "No", "[]");
    }

    @Test
    public void testMatrixWithOneValueGraterThanThreshold() {
        testLowCostPath(SampleInputs.TEST_CASE_9, "14", "Yes", "[3 2 1 3]");
    }

    @Test
    public void testMatrixWithNegativeValues() {
        testLowCostPath(SampleInputs.TEST_CASE_10, "0", "Yes", "[2 3 4 1]");
    }

    @Test
    public void testMatrixWithCompletePathLowerCost() {
        testLowCostPath(SampleInputs.TEST_CASE_11, "10", "Yes", "[4 4]");
    }

    @Test
    public void testMatrixWithLongerIncompletePathShorterLowerCost() {
        testLowCostPath(SampleInputs.TEST_CASE_12, "10", "No", "[4 4]");
    }

    @Test
    public void testMatrixWithLargeNoOfColumns() {
        testLowCostPath(SampleInputs.TEST_CASE_13, "10", "Yes", "[1 1 1 1 1 1 1 1 1 1]");

    }

    @Test
    public void testMatrixWith3X5() {
        testLowCostPath(SampleInputs.TEST_CASE_14, "48", "No", "[1 1 1]");
    }

    @Test
    public void testMatrixWith5X6() {
        testLowCostPath(SampleInputs.TEST_CASE_15, "11", "Yes", "[1 2 1 5 4 5]");
    }

    public void testLowCostPath( int[][] aInput, String aCost, String aStatus, String aPath ) {

        if (aInput == null) {
            return;
        }
        int mRows = aInput.length;
        if (mRows == 0) {
            return;
        }
        int mColumns = aInput[0].length;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText("" + mRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + mColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());

        enterUserInput(aInput);

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.calculate_btn)).perform(click());

        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        onView(withId(R.id.result_status_txt_view)).check(matches(withText(aStatus)));

        onView(withId(R.id.result_cost_txt_view)).check(matches(withText(aCost)));

        onView(withId(R.id.result_path_view)).check(matches(withText(aPath)));

        SystemClock.sleep(TEST_EXE_DELAY);
    }

    /**
     * Method to enter comma separated user input into the generated Rows.
     * 
     * @param aInput 2D Input Matrix
     */
    private void enterUserInput( int[][] aInput ) {

        int lRows = aInput.length;
        int lColumns = aInput[0].length;

        for (int i = 0; i < lRows; i++) {
            StringBuilder lBuilder = new StringBuilder();
            for (int j = 0; j < lColumns; j++) {
                if (j == lColumns - 1) {
                    lBuilder.append(aInput[i][j]);
                } else {
                    lBuilder.append(aInput[i][j]).append(",");
                }
            }
            SystemClock.sleep(TEST_ACTION_DELAY);
            onView(withId(i))
                    .perform(scrollTo(), typeText(lBuilder.toString()), closeSoftKeyboard());
        }

    }

}
