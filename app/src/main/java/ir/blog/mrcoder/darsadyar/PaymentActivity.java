package ir.blog.mrcoder.darsadyar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by emran on 6/16/19.
 */
public class PaymentActivity extends AppCompatActivity {

    private static final int PRICE = 5000;

    private TextView removeAdsDescTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initialize();
        setTexts();
    }

    private void initialize() {
        removeAdsDescTV = findViewById(R.id.removeAdsDescTV);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "bkoodak.ttf");
        removeAdsDescTV.setTypeface(typeface);
    }

    private void setTexts() {
        removeAdsDescTV.setText(PersianReshape.reshape(getResources().getString(R.string.remove_ads_desc, PRICE)));
        getSupportActionBar().setTitle(R.string.remove_ads);
    }
}
