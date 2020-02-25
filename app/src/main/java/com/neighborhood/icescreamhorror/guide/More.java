package com.neighborhood.icescreamhorror.guide;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.utils.BannerUltils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class More extends AppCompatActivity {

    @BindView(R.id.imgBackMore)
    ImageView imgBackMore;

    @BindView(R.id.frameAds1)
    FrameLayout frameAds1;
    @BindView(R.id.frameAds2)
    FrameLayout frameAds2;

    AdView adViewMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        Common.setColorStatusBar(this);
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewMore = new AdView(this);
        adViewMore.setAdSize(AdSize.BANNER);
        adViewMore.setAdUnitId(getString(R.string.id_banner));
        adViewMore.setAdListener(new BannerUltils().adListener(BannerUltils.KEY_CONTRANS_ADS_BANNER_MORE));
        adViewMore.loadAd(adRequest);

        frameAds1.removeAllViews();
        frameAds1.addView(adViewMore);
//        frameAds2.removeAllViews();
//        frameAds2.addView(adViewMore);
    }

    @OnClick(R.id.imgBackMore)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
