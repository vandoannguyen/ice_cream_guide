package com.neighborhood.icescreamhorror.guide.guide_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.guide_detail.GuideDetail;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.utils.BannerUltils;

import java.io.Serializable;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Guide extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.imgBackGuide)
    ImageView imgBackGuide;
    @BindView(R.id.lineAppBar)
    RelativeLayout lineAppBar;
    @BindView(R.id.revViewGuide)
    RecyclerView revViewGuide;
    @BindView(R.id.loadingGuide)
    View loading;


    PublisherInterstitialAd mPublisherInterstitialAd;
    @BindView(R.id.adViewGuide)
    AdView adViewGuide;
    private String TAG = "GUIDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Common.setColorStatusBar(this);

        ButterKnife.bind(this);
        initAds();
        initView();
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.id_interstitial_ad));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewGuide.setAdListener(new BannerUltils().adListener(adViewGuide,BannerUltils.KEY_CONTRANS_ADS_BANNER_GUIDE_VIEW));
        adViewGuide.loadAd(
                adRequest
        );
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        revViewGuide.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setInitialPrefetchItemCount(8);
        GuideItemAdapter adapter = new GuideItemAdapter(Common.listCategory, this);
        revViewGuide.setAdapter(adapter);
        revViewGuide.setHasFixedSize(true);
        adapter.setClickItemListener(new GuideItemAdapter.ClickItemListener() {
            @Override
            public void onClick(int index) {
                intentToDetail(index);
            }
        });
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void intentToDetail(int index) {
//        Intent intent = new Intent(Guide.this, GuideDetail.class);
//        intent.putExtra("data", Common.listCategory.get(index));
//        startActivity(intent);
        intentToScreenAds(GuideDetail.class, Common.listCategory.get(index));
    }

    @OnClick(R.id.imgBackGuide)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void intentToScreenAds(Class guideClass, Serializable data) {

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
                intentToScreen(guideClass, data);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                intentToScreen(guideClass, data);
            }
        });
        loading.setVisibility(View.VISIBLE);
        int random = new Random().nextInt(2);
        Log.e(TAG, "intentToScreenAds: " + random);
        if (random == 1) {
            mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        } else {
            loading.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: ");
                    loading.setVisibility(View.GONE);
                    intentToScreen(guideClass, data);
                }
            }, 2000);
        }

    }

    private void intentToScreen(Class uiClass, Serializable data) {
        Intent intent = new Intent(Guide.this, uiClass);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
