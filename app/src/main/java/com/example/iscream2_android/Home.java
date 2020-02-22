package com.example.iscream2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iscream2_android.guide.Guide;
import com.example.iscream2_android.model.Category;
import com.example.iscream2_android.model.Common;
import com.example.iscream2_android.model.Question;
import com.example.iscream2_android.question.Questions;
import com.example.iscream2_android.utils.CustomDialog;
import com.example.iscream2_android.utils.SharedPrefsUtils;
import com.example.iscream2_android.utils.UnifiedNativeAdsUtils;
import com.example.ratedialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends AppCompatActivity implements RatingDialog.RatingDialogInterFace {
    @BindView(R.id.btnGuide)
    Button btnGuide;
    @BindView(R.id.btnQuestion)
    Button btnQuestion;
    @BindView(R.id.btnAbout)
    Button btnAbout;
    @BindView(R.id.btnMore)
    Button btnMore;
    @BindView(R.id.frameAdsHome)
    FrameLayout frameAdsHome;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    private int backPressCount = 0;
    CustomDialog customDialog;
    private String TAG = "HOME";
    @BindView(R.id.loading)
    View loading;

    private PublisherInterstitialAd mPublisherInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Common.setColorStatusBar(this);
        getDataFromJson(this);
        ButterKnife.bind(this);
        initAds();
        initControl();
        rateAuto();
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.id_interstitial_ad));
        UnifiedNativeAdsUtils.getInstance(this).setNativeAds(frameAdsHome, R.layout.ad_unified_draw_navigator, new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });
        setCustomDialog(this);
    }

    private void initControl() {
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: clicked loading");
            }
        });
    }

    private void getDataFromJson(Context context) {
        Type type1 = new TypeToken<ArrayList<Category>>() {
        }.getType();
        Type type2 = new TypeToken<ArrayList<Question>>() {
        }.getType();
        Common.listCategory = new Gson().fromJson(loadJSONFromAsset(this, "data"), type1);
        Common.listQuestion = new Gson().fromJson(loadJSONFromAsset(this, "question"), type2);
        Log.e("getDataFromJson: ", loadJSONFromAsset(this, "data"));
        Log.e("getDataFromJson: ", Common.listCategory.size() + "");
        Log.e("getDataFromJson: ", Common.listQuestion.size() + "");
    }

    private void setCustomDialog(Home home) {
        customDialog = new CustomDialog(this);
        customDialog.setButtonClickListener(new CustomDialog.ButtonClickListener() {
            @Override
            public void onOkClick() {
                finish();
            }

            @Override
            public void onCancelClick() {
                setCustomDialog(Home.this);
            }
        });
        customDialog.setContent("Do you want to exit app ?");
        customDialog.setLable("Exit app !");
        customDialog.setBannerAds(this, getString(R.string.id_banner));
    }

    @Override
    public void onBackPressed() {
        backPressCount++;
        if (backPressCount == 1)
            Toast.makeText(this, "Press more to exit", Toast.LENGTH_SHORT).show();
        if (backPressCount == 2) {
            showDialogBack(this);
            backPressCount = 0;
        }
    }

    private void showDialogBack(Activity activity) {
        customDialog.show();
    }

    @OnClick({R.id.btnGuide, R.id.btnQuestion, R.id.btnAbout, R.id.btnMore, R.id.imgShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGuide: {
                intentToScreenAds(Guide.class);
                break;
            }
            case R.id.btnQuestion: {
                intentToScreenAds(Questions.class);
                break;
            }

            case R.id.btnAbout: {
                intentToScreenAds(About.class);
            }
            break;
            case R.id.btnMore: {
                intentToScreenAds(More.class);
            }
            break;
            case R.id.imgShare: {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                Log.e(TAG, "onViewClicked: " + "share");
                break;
            }
        }
    }

    private void intentToScreenAds(Class guideClass) {

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mPublisherInterstitialAd.show();
            }

            @Override
            public void onAdOpened() {
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loading.setVisibility(View.GONE);
                intentToScreen(guideClass);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                intentToScreen(guideClass);
            }
        });
        loading.setVisibility(View.VISIBLE);
        int random = new Random().nextInt(3);
        Log.e(TAG, "intentToScreenAds: " + random);
        if (random == 1 || random == 2) {
            mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        } else {
            loading.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: ");
                    loading.setVisibility(View.GONE);
                    intentToScreen(guideClass);
                }
            }, 2000);
        }

    }

    private void intentToScreen(Class uiClass) {
        Intent intent = new Intent(Home.this, uiClass);
        startActivity(intent);
    }

    public String loadJSONFromAsset(Context context, String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static void rateApp(Context context) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);


    }

    public void rateAuto() {
        int rate = SharedPrefsUtils.getInstance(this).getInt("rate");
        if (rate < 1) {
            RatingDialog ratingDialog = new RatingDialog(this);
            ratingDialog.setRatingDialogListener(this);
            ratingDialog.showDialog();
            SharedPrefsUtils.getInstance(this).putInt("rate", 5);
        }

    }

    void rateManual() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(this);
        ratingDialog.showDialog();
    }

    @Override
    public void onDismiss() {
    }

    @Override
    public void onSubmit(float rating) {
        if (rating > 3) {
            rateApp(this);
            SharedPrefsUtils.getInstance(this).putInt("rate", 5);
        }
    }

    @Override
    public void onRatingChanged(float rating) {
    }
}