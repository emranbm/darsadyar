package ir.blog.mrcoder.darsadyar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ir.blog.mrcoder.darsadyar.paymentUtils.IabHelper;
import ir.blog.mrcoder.darsadyar.paymentUtils.IabResult;
import ir.blog.mrcoder.darsadyar.paymentUtils.Inventory;
import ir.blog.mrcoder.darsadyar.paymentUtils.Purchase;

/**
 * Created by emran on 6/16/19.
 */
public class PaymentActivity extends AppCompatActivity implements IabHelper.QueryInventoryFinishedListener, IabHelper.OnIabPurchaseFinishedListener {

    private TextView removeAdsDescTV;
    private Button paymentBtn;
    private ProgressBar loadingPB;

    private static final String SKU = "BannerAdsRemoval";
    private static final String PUBLIC_KEY = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC6uk4g0Zn+cDugzrRo8I0mnQG3wTenSRauSZLJl6n0tp0d62F/113jXBHfzCEOXSP/YILqP6oByNBGy94o7Ka9Zn00YOa6xcD+0qo57cMuERPAJ41rOjVTqwh2HER+kfQhhDoqHYweZDTht40xzdPD5l6ZtvtMnqAPAxv7sLfgPhd44g2yJGLdvDpKQNG6OeY6GeAONRcsxBMhOY3P8mLLhyQTlKA8v5/mJnGabOcCAwEAAQ==";
    private static final int REQ_CODE = 1232;
    private IabHelper iabHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initialize();
        getSupportActionBar().setTitle(R.string.title_activity_payment);
        initPayment();
    }

    private void initialize() {
        removeAdsDescTV = findViewById(R.id.removeAdsDescTV);
        paymentBtn = findViewById(R.id.paymentBtn);
        loadingPB = findViewById(R.id.loadingPB);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "bkoodak.ttf");
        removeAdsDescTV.setTypeface(typeface);
        paymentBtn.setTypeface(typeface);

    }

    private void initPayment() {
        iabHelper = new IabHelper(this, PUBLIC_KEY);
        iabHelper.startSetup(result -> {
            if (result.isFailure())
                Toast.makeText(this, getString(R.string.problem_connecting_cafebazaar), Toast.LENGTH_LONG).show();
            else {
                ArrayList<String> lst = new ArrayList<>();
                lst.add(SKU);
                iabHelper.queryInventoryAsync(true, lst, this);
            }
        });
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
        paymentBtn.setEnabled(true);
        loadingPB.setVisibility(View.GONE);

        if (result.isSuccess()) {
            String price = inv.getSkuDetails(SKU).getPrice();
            paymentBtn.setText(PersianReshape.reshape(getResources().getString(R.string.pay_price, price)));
            final boolean hasPaid = inv.hasPurchase(SKU);
            if (hasPaid) {
                Toast.makeText(this, R.string.payment_successful, Toast.LENGTH_LONG).show();
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

        Toast.makeText(this, R.string.payment_successful, Toast.LENGTH_LONG).show();
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
