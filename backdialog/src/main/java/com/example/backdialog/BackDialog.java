package com.example.backdialog;

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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BackDialog{
    static Boolean IS_NATIVE_ADS= true;
    static Boolean IS_BANNER_ADS= false;
    private static String BANNER_ID = "";
    Activity activity;
    FrameLayout frameLayout;
    TextView txtLable, txtContent;
    Button btnOk, btnHuy;
    AlertDialog alertDialog;
    OnButtonClick onButtonClick;
    private String NATIVE_ADS_ID="";

    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }
    public BackDialog(@NonNull final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_back_lib, viewGroup, false);
        frameLayout = dialogView.findViewById(R.id.frameAds);
        txtLable = dialogView.findViewById(R.id.txtDialogLable);
        txtContent = dialogView.findViewById(R.id.txtDialogCotent);
        btnHuy = dialogView.findViewById(R.id.btnHuy);
        btnOk = dialogView.findViewById(R.id.btnThoat);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClick != null)
                    onButtonClick.yesClick();
                if(!BANNER_ID.equals("")){
                    setBannerAds(activity, BANNER_ID);
                }
                if(!NATIVE_ADS_ID.equals("")){
                    setNativeAds(activity,NATIVE_ADS_ID );
                }
                alertDialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!BANNER_ID.equals("")){
                    setBannerAds(activity, BANNER_ID);
                }
                if(!NATIVE_ADS_ID.equals("")){
                    setNativeAds(activity,NATIVE_ADS_ID );
                }
                if(onButtonClick!= null)
                    onButtonClick.noClick();
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
        BANNER_ID = bannerID;
        NATIVE_ADS_ID = "";
    }

    public void setNativeAds(Activity activity, String idNativeAds) {
        BANNER_ID ="";
        NATIVE_ADS_ID = idNativeAds;
        UnifiedNativeAdsUtils.getInstance(activity).setNativeAds(frameLayout, idNativeAds, new AdListener() {

        });
    }

    public AlertDialog show() {
        alertDialog.show();
        return alertDialog;
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

    public interface OnButtonClick{
        void noClick();
        void yesClick();
    }
}
