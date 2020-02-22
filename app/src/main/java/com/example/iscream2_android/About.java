package com.example.iscream2_android;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iscream2_android.model.Common;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity {

    @BindView(R.id.imgBackAnswer)
    ImageView imgBackAnswer;
    @BindView(R.id.txtQuestionAnswer)
    TextView txtQuestionAnswer;
    @BindView(R.id.adViewAbout)
    AdView adViewAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Common.setColorStatusBar(this);
        ButterKnife.bind(this);
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewAbout.loadAd(adRequest);
    }

    @OnClick(R.id.imgBackAnswer)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
