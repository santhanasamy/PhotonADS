
package com.photon.codechallenge.shortestpath;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests to check the InputCollector Activity.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LowCostPathTest {

    public static final int mLowerBound = 0;

    public static final int mUpperBound = 50;

    @Rule
    public ActivityTestRule<InputCollectorActivity> mActivityRule = new ActivityTestRule(
            InputCollectorActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText("Hello world!")).check(matches(isDisplayed()));
    }

    @Test
    public void testCustomUserInput() {

        int mRows = (int) (Math.random()
                * ((CommonUtils.UI_MAX_ROW - CommonUtils.MIN_NO_OF_ROWS) + 1)
                + CommonUtils.MIN_NO_OF_ROWS);
        int mColumns = (int) (Math.random()
                * ((CommonUtils.UI_MAX_COLUMN - CommonUtils.MIN_NO_OF_COLUMNS) + 1)
                + CommonUtils.MIN_NO_OF_COLUMNS);

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText("" + mRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + mColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());

        for (int i = 0; i < mRows; i++) {
            StringBuilder lBuilder = new StringBuilder();
            for (int j = 0; j < mColumns; j++) {
                int lRandomNo = (int) (Math.random() * ((mUpperBound - mLowerBound) + 1)
                        + mLowerBound);
                if (j == mColumns - 1) {
                    lBuilder.append(lRandomNo);
                } else {
                    lBuilder.append(lRandomNo).append(",");
                }
            }
            SystemClock.sleep(1000);
            onView(withId(i)).perform(typeText(lBuilder.toString()), closeSoftKeyboard());
        }
        SystemClock.sleep(3000);
        onView(withId(R.id.calculate_btn)).perform(click());

        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        SystemClock.sleep(20000);
    }

    @Test
    public void testValidLowCostPathCalculation() {

        int mRows = SampleInputs.MIN_COLUMN_TEST.length;
        int mColumns = SampleInputs.MIN_COLUMN_TEST[0].length;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText("" + mRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + mColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());

        for (int i = 0; i < mRows; i++) {
            StringBuilder lBuilder = new StringBuilder();
            for (int j = 0; j < mColumns; j++) {
                if (j == mColumns - 1) {
                    lBuilder.append(SampleInputs.MIN_COLUMN_TEST[i][j]);
                } else {
                    lBuilder.append(SampleInputs.MIN_COLUMN_TEST[i][j]).append(",");
                }
            }
            SystemClock.sleep(1000);
            onView(withId(i)).perform(typeText(lBuilder.toString()), closeSoftKeyboard());
        }
        SystemClock.sleep(3000);
        onView(withId(R.id.calculate_btn)).perform(click());

        onView(withId(R.id.result_status_lable_txt_view)).check(matches(withText("Status")));
        onView(withId(R.id.result_status_txt_view)).check(matches(withText("Yes")));

        onView(withId(R.id.result_cost_txt_view)).check(matches(withText("16")));

        onView(withId(R.id.result_path_view)).check(matches(withText("[1 2 3 4 4 5]")));

        SystemClock.sleep(20000);
    }
}
