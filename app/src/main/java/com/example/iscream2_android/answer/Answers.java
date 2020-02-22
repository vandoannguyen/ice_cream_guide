package com.example.iscream2_android.answer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iscream2_android.R;
import com.example.iscream2_android.model.Answer;
import com.example.iscream2_android.model.Common;
import com.example.iscream2_android.model.Question;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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
