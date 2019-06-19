package ir.blog.mrcoder.darsadyar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.ronash.pushe.Pushe;
import ir.adad.client.Adad;
import ir.blog.mrcoder.darsadyar.utils.PaymentHelper;

public class MainActivity extends Activity {

    private TextView kolTV, nazadeTV, ghalatTV, dorostTV, resultTV, cautionTV;
    private EditText kolET, nazadeET, ghalatET, dorostET;
    private ImageView removeAdsIV;
    private int kol = -1, nazade = -1, ghalat = -1, dorost = -1;
    private static final long ANIMATION_TIME = 250;

    private static final int REQ_REMOVE_ADS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Adad.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        initialize();

        removeAdsIfPaid();

        setListeners();

        Pushe.initialize(this,false);
    }

    private void initialize() {
        kolTV = findViewById(R.id.kolTV);
        nazadeTV = findViewById(R.id.nazadeTV);
        ghalatTV = findViewById(R.id.ghalatTV);
        dorostTV = findViewById(R.id.dorostTV);
        resultTV = findViewById(R.id.resultTV);
        cautionTV = findViewById(R.id.cautionTV);
        kolET = findViewById(R.id.kolET);
        nazadeET = findViewById(R.id.nazadeET);
        ghalatET = findViewById(R.id.ghalatET);
        dorostET = findViewById(R.id.dorostET);
        removeAdsIV = findViewById(R.id.removeAdsIV);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "bkoodak.ttf");

        kolTV.setTypeface(typeface);
        nazadeTV.setTypeface(typeface);
        ghalatTV.setTypeface(typeface);
        dorostTV.setTypeface(typeface);
        cautionTV.setTypeface(typeface);

        kolTV.setText(PersianReshape.reshape(kolTV.getText().toString()));
        nazadeTV.setText(PersianReshape.reshape(nazadeTV.getText().toString()));
        ghalatTV.setText(PersianReshape.reshape(ghalatTV.getText().toString()));
        dorostTV.setText(PersianReshape.reshape(dorostTV.getText().toString()));
        cautionTV.setText(PersianReshape.reshape(cautionTV.getText().toString()));
    }

    private void setListeners() {
        kolET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    kol = -1;
                else
                    kol = Integer.valueOf(s.toString());

                calculatePercent();
            }
        });

        nazadeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    nazade = -1;
                else
                    nazade = Integer.valueOf(s.toString());
                calculatePercent();
            }
        });

        ghalatET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    ghalat = -1;
                else
                    ghalat = Integer.valueOf(s.toString());
                calculatePercent();

            }
        });

        dorostET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    dorost = -1;
                else
                    dorost = Integer.valueOf(s.toString());
                calculatePercent();
            }
        });
    }

    private void calculatePercent() {
        if (kol == -1) {
            if (dorost == -1) {
                if (ghalat == -1) {
                    if (nazade == -1) {
                        setPercent("?");
                    } else {
                        setPercent(0f);
                    }
                } else {
                    if (nazade == -1) {
                        setPercent(-33.33f);
                    } else {
                        setPercent(calc(0, ghalat, nazade));
                    }
                }
            } else {
                if (ghalat == -1) {
                    if (nazade == -1) {
                        setPercent(100f);
                    } else {
                        setPercent(calc(dorost, 0, nazade));
                    }
                } else {
                    if (nazade == -1) {
                        setPercent(calc(dorost, ghalat, 0));
                    } else {
                        setPercent(calc(dorost, ghalat, nazade));
                    }
                }
            }
        } else {
            if (dorost == -1) {
                if (ghalat == -1) {
                    if (nazade == -1) {
                        setPercent("?");
                    } else {
                        setPercent(calc(kol - nazade, 0, nazade));
                        if (nazade > kol)
                            setPercent("!");
                    }
                } else {
                    if (nazade == -1) {
                        setPercent(calc(kol - ghalat, ghalat, 0));
                        if (ghalat > kol)
                            setPercent("!");
                    } else {
                        setPercent(calc(kol - ghalat - nazade, ghalat, nazade));
                        if (ghalat + nazade > kol)
                            setPercent("!");
                    }
                }
            } else {
                if (ghalat == -1) {
                    if (nazade == -1) {
                        setPercent(calc(dorost, 0, kol - dorost));
                        if (dorost > kol)
                            setPercent("!");
                    } else {
                        setPercent(calc(dorost, kol - dorost - nazade, nazade));
                        if (dorost + nazade > kol)
                            setPercent("!");
                    }
                } else {
                    if (nazade == -1) {
                        setPercent(calc(dorost, ghalat, kol - dorost - ghalat));
                        if (dorost + ghalat > kol)
                            setPercent("!");
                    } else {
                        if (kol == dorost + ghalat + nazade)
                            setPercent(calc(dorost, ghalat, nazade));
                        else
                            setPercent("!");
                    }
                }
            }
        }
    }

    private void setPercent(float percent) {
        percent = ((float) (int) (percent * 100)) / 100;
        setPercent(percent + "%");
    }

    private void setPercent(final String value) {
        Animation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(ANIMATION_TIME);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                resultTV.setText(value);
                Animation animation2 = new AlphaAnimation(0f, 1f);
                animation2.setDuration(ANIMATION_TIME);
                resultTV.startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        resultTV.startAnimation(animation);
    }

    private float calc(int dorost, int ghalat, int nazade) {
        return (dorost - (float) ghalat / 3) / (dorost + ghalat + nazade) * 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle(PersianReshape.reshape(menu.getItem(0).getTitle().toString()));
        menu.getItem(1).setTitle(PersianReshape.reshape(menu.getItem(1).getTitle().toString()));
        menu.getItem(2).setTitle(PersianReshape.reshape(menu.getItem(2).getTitle().toString()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.aboutMenu:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.otherMenu:
                Intent otherAppsIntent = new Intent(Intent.ACTION_VIEW);
                otherAppsIntent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=mrcoder"));
                otherAppsIntent.setPackage("com.farsitel.bazaar");
                try {
                    startActivity(otherAppsIntent);
                } catch (Exception e) {
                    Toast.makeText(this, PersianReshape.reshape("بازار نصب نیست!"), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.likeMenu:
                Intent likeIntent = new Intent(Intent.ACTION_EDIT);
                likeIntent.setData(Uri.parse("bazaar://details?id=ir.blog.mrcoder.darsadyar"));
                likeIntent.setPackage("com.farsitel.bazaar");
                try {
                    startActivity(likeIntent);
                } catch (Exception e) {
                    Toast.makeText(this, PersianReshape.reshape("بازار نصب نیست!"), Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRemoveAdsClicked(View view) {
        Intent intent = new Intent(this.getApplicationContext(), PaymentActivity.class);
        startActivityForResult(intent, REQ_REMOVE_ADS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_REMOVE_ADS)
            removeAdsIfPaid();
    }

    private void removeAdsIfPaid() {
        boolean isAdDisabled = PaymentHelper.IsAdDisabled(this);
        if (isAdDisabled) {
            Adad.disableBannerAds();
            removeAdsIV.setVisibility(View.INVISIBLE);
        }
        else {
            Adad.enableBannerAds();
            removeAdsIV.setVisibility(View.VISIBLE);
        }
    }
}
