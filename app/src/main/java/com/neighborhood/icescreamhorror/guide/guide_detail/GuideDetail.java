package com.neighborhood.icescreamhorror.guide.guide_detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.model.Category;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideDetail extends AppCompatActivity {

    @BindView(R.id.imgBackGuideDetail)
    ImageView imgBackGuideDetail;
    @BindView(R.id.titleGuideDetail)
    TextView titleGuideDetail;
    @BindView(R.id.revViewGuideDetail)
    RecyclerView revViewGuideDetail;
    @BindView(R.id.adViewGuideDetail)
    AdView adViewGuideDetail;

    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);
        Common.setColorStatusBar(this);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("data");
        titleGuideDetail.setText(category.getName());
        initAds();
        intControll();
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewGuideDetail.loadAd(adRequest);
    }

    private void intControll() {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        GuideDetailAdapter adapter = new GuideDetailAdapter((ArrayList<String>) category.getDescription());
        revViewGuideDetail.setLayoutManager(manager);
        revViewGuideDetail.setHasFixedSize(true);
        revViewGuideDetail.setAdapter(adapter);
    }

    @OnClick(R.id.imgBackGuideDetail)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
