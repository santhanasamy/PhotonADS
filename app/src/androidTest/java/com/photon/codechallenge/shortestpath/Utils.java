
package com.photon.codechallenge.shortestpath;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.view.ViewGroup;

import com.photon.codechallenge.shortestpath.utils.CommonUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Test utils
 */

public class Utils {

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
