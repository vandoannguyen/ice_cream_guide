package com.example.iscream2_android.question;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iscream2_android.Home;
import com.example.iscream2_android.R;
import com.example.iscream2_android.answer.Answers;
import com.example.iscream2_android.model.Answer;
import com.example.iscream2_android.model.Common;
import com.example.iscream2_android.model.Question;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.io.Serializable;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Questions extends AppCompatActivity {

    private static final String TAG = "Questions";
    @BindView(R.id.revQuestion)
    RecyclerView revQuestion;
    @BindView(R.id.imgBackQuestion)
    ImageView imgBackQuestion;
    @BindView(R.id.adViewQuestion)
    AdView adViewQuestion;
    @BindView(R.id.loading_question)
    View loading;
    PublisherInterstitialAd mPublisherInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        Common.setColorStatusBar(this);
        setListView();
        initAds();
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.id_interstitial_ad));
        AdRequest adRequest = new AdRequest.Builder().build();
//        AdSize adSize = getAdSize();
//        adViewGuide.setAdSize(adSize);
        adViewQuestion.loadAd(
                adRequest
        );
    }

    private void setListView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        revQuestion.setLayoutManager(manager);
        revQuestion.setHasFixedSize(true);
        QuestionAdapter adapter = new QuestionAdapter(Common.listQuestion);
        adapter.setClickItemListener(new QuestionAdapter.ClickItemListener() {
            @Override
            public void onClick(int index) {
                intentToScreenAds(Answers.class, Common.listQuestion.get(index));
            }
        });
        revQuestion.setAdapter(adapter);
    }

    private void intentToDetail(int index) {
        Intent intent = new Intent(Questions.this, Answers.class);
        intent.putExtra("data", Common.listQuestion.get(index));
        startActivity(intent);
    }

    @OnClick(R.id.imgBackQuestion)
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
                intentToScreen(guideClass,data);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                intentToScreen(guideClass,data);
            }
        });
        loading.setVisibility(View.VISIBLE);
        int random = new Random().nextInt(3);
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
                    intentToScreen(guideClass,data);
                }
            }, 2000);
        }

    }

    private void intentToScreen(Class uiClass, Serializable data) {
        Intent intent = new Intent(Questions.this, uiClass);
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
