package ir.blog.mrcoder.darsadyar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.function.Consumer;

import ir.blog.mrcoder.darsadyar.R;
import ir.blog.mrcoder.darsadyar.paymentUtils.IabHelper;
import ir.blog.mrcoder.darsadyar.paymentUtils.IabResult;

/**
 * Created by emran on 6/17/19.
 */
public final class PaymentHelper {

    private static final String SP_AD_DISABLED = "SP_AD_DISABLED";

    public static boolean IsAdDisabled(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(SP_AD_DISABLED, false);
    }

    public static void SetAdsDisabled(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.edit().putBoolean(SP_AD_DISABLED, true).commit())
            Toast.makeText(context, context.getString(R.string.error_writing_sp), Toast.LENGTH_LONG).show();
    }
}
