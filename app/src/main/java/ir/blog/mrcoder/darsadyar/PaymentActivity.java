package ir.blog.mrcoder.darsadyar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ir.blog.mrcoder.darsadyar.paymentUtils.IabHelper;
import ir.blog.mrcoder.darsadyar.paymentUtils.IabResult;
import ir.blog.mrcoder.darsadyar.paymentUtils.Inventory;
import ir.blog.mrcoder.darsadyar.paymentUtils.Purchase;
import ir.blog.mrcoder.darsadyar.utils.PaymentHelper;

/**
 * Created by emran on 6/16/19.
 */
public class PaymentActivity extends AppCompatActivity implements IabHelper.QueryInventoryFinishedListener, IabHelper.OnIabPurchaseFinishedListener {

    private static final int PRICE = 5000;

    private TextView removeAdsDescTV;
    private Button paymentBtn;

    private static final String SKU = "BannerAdsRemoval";
    private static final int REQ_CODE = 1232;
    private IabHelper iabHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initialize();

        getSupportActionBar().setTitle(R.string.remove_ads);

        setAppropriateTexts();

        initPayment();
    }

    private void initialize() {
        removeAdsDescTV = findViewById(R.id.removeAdsDescTV);
        paymentBtn = findViewById(R.id.paymentBtn);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "bkoodak.ttf");
        removeAdsDescTV.setTypeface(typeface);
        paymentBtn.setTypeface(typeface);

    }

    private void initPayment() {
        iabHelper = new IabHelper(this, BuildConfig.ADAD_BASE64_KEY);
        iabHelper.startSetup(result -> {
            if (result.isFailure())
                Toast.makeText(this, getString(R.string.problem_connecting_cafebazaar), Toast.LENGTH_LONG).show();
            else {
                paymentBtn.setEnabled(true);
                iabHelper.queryInventoryAsync(this);
            }
        });
    }

    private void setAppropriateTexts() {
        if (PaymentHelper.IsAdDisabled(this))
            removeAdsDescTV.setText(PersianReshape.reshape(getResources().getString(R.string.ads_already_disabled)));
        else
            removeAdsDescTV.setText(PersianReshape.reshape(getResources().getString(R.string.remove_ads_desc, PRICE)));
    }

    public void onPaymentBtnClicked(View view) {
        iabHelper.launchPurchaseFlow(this, SKU, REQ_CODE, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass on the activity result to the helper for handling
        if (!iabHelper.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isSuccess()) {
            final boolean hasPaid = inv.hasPurchase(SKU);
            final boolean notDisabledBefore = !PaymentHelper.IsAdDisabled(this);
            if (hasPaid && notDisabledBefore) {
                PaymentHelper.SetAdsDisabled(this);
                setAppropriateTexts();
                Toast.makeText(this, R.string.ads_removed, Toast.LENGTH_LONG).show();
                finish();
            }
        } else
            Toast.makeText(this, R.string.error_querying_inventory, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isFailure()) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), R.string.payement_failed, Toast.LENGTH_LONG).show();
            return;
        }

        PaymentHelper.SetAdsDisabled(this);
        Toast.makeText(this, R.string.ads_removed, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (iabHelper != null)
            iabHelper.dispose();
        iabHelper = null;
    }
}
