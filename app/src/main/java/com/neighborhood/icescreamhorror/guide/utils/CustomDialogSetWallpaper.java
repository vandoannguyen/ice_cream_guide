package com.neighborhood.icescreamhorror.guide.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.button.MaterialButton;
import com.neighborhood.icescreamhorror.guide.R;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomDialogSetWallpaper implements View.OnClickListener {

    AlertDialog alertDialog;
    @BindView(R.id.frameAdsDialogSetWallpaper)
    FrameLayout frameAdsDialogSetWallpaper;
    @BindView(R.id.txtDialogWallpaperLabel)
    TextView txtDialogWallpaperLabel;

    OnButtonClickListener onButtonClickListener;
    MaterialButton btnSystem, btnBoth, btnLock;
    Activity activity;
    String TAG = "CustomDialogSetWallpaper";

    public CustomDialogSetWallpaper(@NonNull Activity activity) {
        this.activity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_set_wallpaper, viewGroup, false);
        inintView(dialogView);
        frameAdsDialogSetWallpaper = dialogView.findViewById(R.id.frameAdsDialogSetWallpaper);
        txtDialogWallpaperLabel = dialogView.findViewById(R.id.txtDialogWallpaperLabel);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void inintView(View dialogView) {
        btnBoth = dialogView.findViewById(R.id.btnBoth);
        btnSystem = dialogView.findViewById(R.id.btnSystem);
        btnLock = dialogView.findViewById(R.id.btLock);
        btnBoth.setOnClickListener(this);
        btnSystem.setOnClickListener(this);
        btnLock.setOnClickListener(this);
    }

    public void setBannerAds(String bannerID) {
        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(bannerID);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        if (frameAdsDialogSetWallpaper != null)
            frameAdsDialogSetWallpaper.removeAllViews();
        frameAdsDialogSetWallpaper.addView(adView);
    }

    public void setNativeAds(String idNativeAds) {
        UnifiedNativeAdsUtils.getInstance(activity).setNativeAds(frameAdsDialogSetWallpaper,
                idNativeAds,
                R.layout.ad_unified_dialog_no_button,
                new AdListener() {

                });
    }

    public void show() {
        alertDialog.show();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onViewClicked: ");
        switch (view.getId()) {
            case R.id.btnSystem: {
                alertDialog.dismiss();
                if (onButtonClickListener != null) {
                    onButtonClickListener.system();
                }
            }
            break;
            case R.id.btLock:
                alertDialog.dismiss();
                if (onButtonClickListener != null) {
                    onButtonClickListener.lock();
                }
                break;
            case R.id.btnBoth:
                alertDialog.dismiss();
                if (onButtonClickListener != null) {
                    onButtonClickListener.both();
                }
                break;
        }
    }

//    @OnClick({R.id.btnSystem, R.id.btLock, R.id.btnBoth})
//    public void onViewClicked(View view) {
//        Log.d(TAG, "onViewClicked: ");
//        switch (view.getId()) {
//            case R.id.btnSystem: {
//                alertDialog.dismiss();
//                if (onButtonClickListener != null) {
//                    onButtonClickListener.system();
//                }
//            }
//            break;
//            case R.id.btLock:
//                alertDialog.dismiss();
//
//                if (onButtonClickListener != null) {
//                    onButtonClickListener.lock();
//                }
//                break;
//            case R.id.btnBoth:
//                alertDialog.dismiss();
//                if (onButtonClickListener != null) {
//                    onButtonClickListener.both();
//                }
//                break;
//        }
//    }

    public interface OnButtonClickListener {
        void system();

        void lock();

        void both();
    }
}
