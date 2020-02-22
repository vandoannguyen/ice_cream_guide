package com.neighborhood.icescreamhorror.guide.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.neighborhood.icescreamhorror.guide.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class CustomDialog {
    Activity activity;
    FrameLayout frameLayout;
    TextView txtLable, txtContent;
    Button btnOk, btnHuy;
    AlertDialog alertDialog;
    ButtonClickListener buttonClickListener;

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public CustomDialog(@NonNull Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_back, viewGroup, false);
        frameLayout = dialogView.findViewById(R.id.frameAds);
        txtLable = dialogView.findViewById(R.id.txtDialogLable);
        txtContent = dialogView.findViewById(R.id.txtDialogCotent);
        btnHuy = dialogView.findViewById(R.id.btnHuy);
        btnOk = dialogView.findViewById(R.id.btnThoat);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonClickListener != null)
                    buttonClickListener.onOkClick();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClickListener.onCancelClick();
                alertDialog.dismiss();
            }
        });
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setLable(String lable) {
        txtLable.setText(lable);
    }

    public void setContent(String content) {
        txtContent.setText(content);
    }

    public void setBannerAds(Activity activity, String bannerID) {
        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(bannerID);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        if (frameLayout != null)
            frameLayout.removeAllViews();
        frameLayout.addView(adView);
    }

    public void setNativeAds(Activity activity, int layout) {
        UnifiedNativeAdsUtils.getInstance(activity).setNativeAds(frameLayout, layout, new AdListener() {

        });
    }

    public void show() {
        alertDialog.show();
    }

    public interface ButtonClickListener {
        void onOkClick();

        void onCancelClick();
    }

    private AdSize getAdSize(Activity context) {  // Step 3 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);
//        return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth - 110);
    }

}
