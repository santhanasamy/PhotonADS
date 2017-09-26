
package com.photon.codechallenge.shortestpath.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.photon.codechallenge.shortestpath.R;
import com.photon.codechallenge.shortestpath.TestConstants;
import com.photon.codechallenge.shortestpath.TestUtils;
import com.photon.codechallenge.shortestpath.utils.CommonUtils;
import com.photon.codechallenge.shortestpath.utils.SampleInputs;

import org.junit.Before;
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
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.photon.codechallenge.shortestpath.TestConstants.LOWER_BOUND;
import static com.photon.codechallenge.shortestpath.TestConstants.TEST_ACTION_DELAY;
import static com.photon.codechallenge.shortestpath.TestConstants.TEST_EXE_DELAY;
import static com.photon.codechallenge.shortestpath.TestConstants.UPPER_BOUND;
import static org.hamcrest.CoreMatchers.not;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class InputCollectorActivityTest {

    /* @Test public void testStartInputCollectorActivity() { //Intents.init();
     * //intending(hasComponent(hasShortClassName(".InputCollectorActivity"))); //
     * intended(hasComponent(InputCollectorActivity.class.getName()), times(1));
     * //Intents.release(); mActivityRule.launchActivity(null); } */

    @Rule
    public IntentsTestRule<InputCollectorActivity> mIntentRule = new IntentsTestRule<>(
            InputCollectorActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

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
    public void testCalculateButtonDisabledState() {

        String lRows = "" + CommonUtils.UI_MAX_ROW;
        String lColumn = "" + CommonUtils.UI_MAX_COLUMN;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRows), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view)).perform(typeText(lColumn), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TestConstants.TEST_ACTION_DELAY);
        lRows = "" + (CommonUtils.UI_MAX_ROW - 1);
        onView(withId(R.id.rows_edit_txt_view)).perform(clearText()).perform(click()).perform(
                typeText(lRows),
                closeSoftKeyboard());
        SystemClock.sleep(TestConstants.TEST_ACTION_DELAY);
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

        int lNoOfRows = TestUtils.getRandomColumnCountWithInBound();
        int lNoOfColumns = TestUtils.getRandomRowCountWithInBound();
        String lRowStr = "" + lNoOfRows;

        onView(withId(R.id.rows_edit_txt_view)).perform(typeText(lRowStr), closeSoftKeyboard());
        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);

        onView(withId(R.id.input_container)).check(
                matches(TestUtils.withChildViewCount(lNoOfRows, withId(R.id.input_container))));

        SystemClock.sleep(TEST_EXE_DELAY);
    }

    @Test
    public void testWithRandomUserInputs() {

        int lNoOfRows = TestUtils.getRandomColumnCountWithInBound();
        int lNoOfColumns = TestUtils.getRandomRowCountWithInBound();

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + lNoOfRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        onView(withId(R.id.generate_row_btn)).perform(click());

        for (int i = 0; i < lNoOfRows; i++) {
            StringBuilder lBuilder = new StringBuilder();
            for (int j = 0; j < lNoOfColumns; j++) {
                int lRandomNo = (int) (Math.random() * ((UPPER_BOUND - LOWER_BOUND) + 1)
                        + LOWER_BOUND);
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

        int lNoOfRows = TestUtils.getRandomColumnCountWithInBound();
        int lNoOfColumns = TestUtils.getRandomRowCountWithInBound();

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
                .check(matches(TestUtils.withChildViewCount(0, withId(R.id.input_container))));

    }

    /**
     * Test the behaviour of the clear button.
     */
    @Test
    public void testClearButtonWithClick() {
        int lNoOfRows = TestUtils.getRandomColumnCountWithInBound();
        int lNoOfColumns = TestUtils.getRandomRowCountWithInBound();

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + lNoOfRows), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + lNoOfColumns), closeSoftKeyboard());

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.generate_row_btn)).perform(click());

    }

    /**
     * Test whether the intended
     * activity{@link com.photon.codechallenge.shortestpath.ui.PathFinderActivity}
     * is launched after clicking on the calculate button.
     */
    @Test
    public void testPathFinderActivityLaunch() {

        onView(withId(R.id.rows_edit_txt_view))
                .perform(typeText("" + SampleInputs.TEST_CASE_1.length), closeSoftKeyboard());

        onView(withId(R.id.columns_edit_txt_view))
                .perform(typeText("" + SampleInputs.TEST_CASE_1[0].length), closeSoftKeyboard());

        SystemClock.sleep(TEST_ACTION_DELAY);
        onView(withId(R.id.generate_row_btn)).perform(click());
        SystemClock.sleep(TEST_ACTION_DELAY);

        TestUtils.enterUserInput(SampleInputs.TEST_CASE_1);

        onView(withId(R.id.calculate_btn)).perform(click());

        intending(hasComponent(hasShortClassName(".PathFinderActivity")));
        SystemClock.sleep(TEST_EXE_DELAY);
    }

    private static class InputCollectorRule<E extends InputCollectorActivity>
            extends ActivityTestRule<E> {

        public InputCollectorRule(Class<E> activityClass) {
            super(activityClass);
        }

        @Override
        protected Intent getActivityIntent() {
            return super.getActivityIntent();
        }

        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            // maybe you want to do something here
        }

        @Override
        protected void afterActivityFinished() {
            super.afterActivityFinished();
            // Clean up mocks
        }

    }
}
