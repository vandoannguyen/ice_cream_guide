package com.example.iscream2_android;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iscream2_android.model.Common;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class More extends AppCompatActivity {

    @BindView(R.id.imgBackMore)
    ImageView imgBackMore;
    @BindView(R.id.adViewMore)
    AdView adViewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        Common.setColorStatusBar(this);
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewMore.loadAd(adRequest);
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
