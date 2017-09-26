
package com.photon.codechallenge.shortestpath.ui;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.photon.codechallenge.shortestpath.R;
import com.photon.codechallenge.shortestpath.TestUtils;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.photon.codechallenge.shortestpath.TestConstants.TEST_ACTION_DELAY;
import static com.photon.codechallenge.shortestpath.TestConstants.TEST_EXE_DELAY;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PathFinderActivityTest {

    @Rule
    public ActivityTestRule<InputCollectorActivity> mActivityRule = new ActivityTestRule(
            InputCollectorActivity.class, false, true);

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

    public void testLowCostPath(int[][] aInput, String aCost, String aStatus, String aPath) {

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

        TestUtils.enterUserInput(aInput);

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.calculate_btn)).perform(click());

        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        onView(withId(R.id.result_status_txt_view)).check(matches(withText(aStatus)));

        onView(withId(R.id.result_cost_txt_view)).check(matches(withText(aCost)));

        onView(withId(R.id.result_path_view)).check(matches(withText(aPath)));

        SystemClock.sleep(TEST_EXE_DELAY);
    }

    /**
     * Test the behaviour of the clear button.
     */
    @Test
    public void testClearButton() {

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + SampleInputs.TEST_CASE_1.length), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + SampleInputs.TEST_CASE_1[0].length), closeSoftKeyboard());

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);

        TestUtils.enterUserInput(SampleInputs.TEST_CASE_1);

        onView(withId(R.id.calculate_btn)).perform(click());

        SystemClock.sleep(TEST_ACTION_DELAY);

        onView(withId(R.id.clear_btn)).perform(click());
        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        onView(withId(R.id.result_status_txt_view)).check(matches(withText("")));

        onView(withId(R.id.result_cost_txt_view)).check(matches(withText("")));

        onView(withId(R.id.result_path_view)).check(matches(withText("")));

        SystemClock.sleep(TEST_EXE_DELAY);
    }
}
