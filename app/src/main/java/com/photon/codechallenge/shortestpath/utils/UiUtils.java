
package com.photon.codechallenge.shortestpath.utils;

import android.content.Context;
import android.widget.Toast;

import com.photon.codechallenge.shortestpath.R;

/**
 * Utils for UI
 */

public class UiUtils {

    private static float textSizeBasedOnTextLength;

    /**
     * Method to check whether the supplied input is valid.
     *
     * @param str Input string
     * @return True If the input is in valid format
     */
    public static boolean isValidInput( String str ) {
        if (str == null) {
            return false;
        }
        return str.matches("-?\\d+");
    }

    /**
     * Method to show invalid input warning,
     *
     * @param aContext
     */
    public static final void showInvalidWarning( Context aContext ) {
        Toast.makeText(aContext, R.string.invalid_input, Toast.LENGTH_SHORT).show();

    }

    public static float getTextSizeBasedOnTextLength( Context aContext, int aInputValue ) {

        if (aInputValue > 999) {
            return aContext.getResources().getDimension(R.dimen.grid_item_text_size_4);
        } else if (aInputValue > 99 && aInputValue < 1000) {
            return aContext.getResources().getDimension(R.dimen.grid_item_text_size_3);
        } else if (aInputValue > 9 && aInputValue < 100) {
            return aContext.getResources().getDimension(R.dimen.grid_item_text_size_2);
        }
        return aContext.getResources().getDimension(R.dimen.grid_item_text_size_1);
    }
}
