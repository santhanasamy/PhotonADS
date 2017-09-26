
package com.photon.codechallenge.shortestpath;

import android.os.SystemClock;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.view.ViewGroup;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Common utilities used for Test
 */

public class TestUtils {

    /**
     * Method to enter comma separated user input into the generated Rows.
     *
     * @param aInput 2D Input Matrix
     */
    public static void enterUserInput( int[][] aInput ) {

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
            SystemClock.sleep(TestConstants.TEST_ACTION_DELAY);
            onView(withId(i))
                    .perform(scrollTo(), typeText(lBuilder.toString()), closeSoftKeyboard());
        }

    }

    /**
     * Method to test the no of children's in a viewgroup
     * 
     * @param count Expected view count
     * @param childMatcher Child matcher
     * @return
     */
    public static Matcher<View> withChildViewCount(
            final int count,
            final Matcher<View> childMatcher ) {
        return new BoundedMatcher<View, ViewGroup>(ViewGroup.class) {
            @Override
            protected boolean matchesSafely( ViewGroup viewGroup ) {
                return viewGroup.getChildCount() == count;
            }

            @Override
            public void describeTo( Description description ) {
                description.appendText("ViewGroup with child-count=" + count + " and");
                childMatcher.describeTo(description);
            }
        };
    }

    public static int getRandomRowCountWithInBound() {

        return (int) (Math.random() * ((CommonUtils.UI_MAX_ROW - CommonUtils.MIN_NO_OF_ROWS) + 1)
                + CommonUtils.MIN_NO_OF_ROWS);
    }

    public static int getRandomColumnCountWithInBound() {
        return (int) (Math.random()
                * ((CommonUtils.UI_MAX_COLUMN - CommonUtils.MIN_NO_OF_COLUMNS) + 1)
                + CommonUtils.MIN_NO_OF_COLUMNS);
    }
}
