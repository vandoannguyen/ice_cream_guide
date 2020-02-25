package com.neighborhood.icescreamhorror.guide.answer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.model.Answer;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.model.Question;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.neighborhood.icescreamhorror.guide.utils.BannerUltils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Answers extends AppCompatActivity {

    @BindView(R.id.imgBackAnswer)
    ImageView imgBackAnswer;
    @BindView(R.id.txtQuestionAnswer)
    TextView txtQuestionAnswer;
    @BindView(R.id.revAnswer)
    RecyclerView revAnswer;
    @BindView(R.id.adAnswer)
    AdView adAnswer;
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Common.setColorStatusBar(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("data");
        setData();
        intiAds();

    }

    private void intiAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        AdRequest adRequest = new AdRequest.Builder().build();
        adAnswer.setAdListener(new BannerUltils().adListener(BannerUltils.KEY_CONTRANS_ADS_BANNER_ANSWER));
        adAnswer.loadAd(adRequest);
    }

    private void setData() {
        txtQuestionAnswer.setText(question.getQuestion());
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        AnswerAdapter answerAdapter = new AnswerAdapter((ArrayList<Answer>) question.getAnswer());
        revAnswer.setHasFixedSize(true);
        revAnswer.setLayoutManager(manager);
        revAnswer.setAdapter(answerAdapter);
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
