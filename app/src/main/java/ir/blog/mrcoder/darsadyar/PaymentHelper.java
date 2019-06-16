package ir.blog.mrcoder.darsadyar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by emran on 6/17/19.
 */
public final class PaymentHelper {

    private static final String SP_AD_DISABLED = "AD_DISABLED";

    public static boolean IsAdDisabled(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(SP_AD_DISABLED, false);
    }

    public static void SetAdsDisabled(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(SP_AD_DISABLED, true);
    }
}
