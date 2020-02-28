package com.example.backdialog;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class UnifiedNativeAdsUtils {
    private Activity activity;
    private static UnifiedNativeAdsUtils sUnifiedNativeAdsUtils;
    private UnifiedNativeAd nativeAd;

    private UnifiedNativeAdsUtils(Activity activity) {
        this.activity = activity;
        MobileAds.initialize(activity.getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
    }

    public static UnifiedNativeAdsUtils getInstance(Activity activity) {
        return sUnifiedNativeAdsUtils == null ? sUnifiedNativeAdsUtils = new UnifiedNativeAdsUtils(activity) : sUnifiedNativeAdsUtils;
    }

    public void setNativeAds(final FrameLayout frameLayout, String nativeAdsId, final AdListener adListener) {
        AdLoader.Builder builder = new AdLoader.Builder(this.activity, nativeAdsId).withAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adListener.onAdClosed();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                adListener.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                adListener.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                adListener.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                adListener.onAdImpression();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adListener.onAdFailedToLoad(errorCode);
//                refresh.setEnabled(true);
                if (frameLayout != null) frameLayout.removeAllViews();
                Toast.makeText(activity, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adListener.onAdLoaded();
            }
        });
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                UnifiedNativeAdView adView = (UnifiedNativeAdView) activity.getLayoutInflater()
                        .inflate(R.layout.ad_unified_dialog_lib, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });
        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(startVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.build();
        adLoader.loadAd(new PublisherAdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView adView) {
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        if (adView.getPriceView() != null) {
            if (unifiedNativeAd.getPrice() == null)
                adView.getPriceView().setVisibility(View.GONE);
            else {
                ((TextView) adView.getPriceView()).setText(unifiedNativeAd.getPrice());
            }
        }
        if (adView.getIconView() != null) {
            if (unifiedNativeAd.getIcon() == null)
                adView.getIconView().setVisibility(View.GONE);
            else {
                adView.getIconView().setVisibility(View.VISIBLE);
                ((ImageView) adView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }
        }
        if (adView.getHeadlineView() != null) {
            if (unifiedNativeAd.getHeadline() == null)
                adView.getHeadlineView().setVisibility(View.GONE);
            else {
                adView.getHeadlineView().setVisibility(View.VISIBLE);
                ((TextView) adView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            }
        }
        if (adView.getAdvertiserView() != null) {
            if (unifiedNativeAd.getAdvertiser() == null)
                adView.getAdvertiserView().setVisibility(View.GONE);
            else {
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
                ((TextView) adView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            }
        }
        if (adView.getBodyView() != null) {
            if (unifiedNativeAd.getBody() == null)
                adView.getBodyView().setVisibility(View.GONE);
            else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
        }
        if (adView.getStarRatingView() != null) {
            if (unifiedNativeAd.getStarRating() == null)
                adView.getStarRatingView().setVisibility(View.GONE);
            else {
                adView.getStarRatingView().setVisibility(View.VISIBLE);
                ((RatingBar) adView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            }
        }
        if (adView.getMediaView() != null) {
            if (unifiedNativeAd.getMediaContent() == null)
                adView.getMediaView().setVisibility(View.GONE);
            else {
                adView.getMediaView().setVisibility(View.VISIBLE);
                ((MediaView) adView.getMediaView()).setMediaContent(unifiedNativeAd.getMediaContent());
            }
        }
        if (adView.getPriceView() != null) {
            if (unifiedNativeAd.getPrice() == null)
                adView.getPriceView().setVisibility(View.GONE);
            else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(unifiedNativeAd.getPrice());
                if (unifiedNativeAd.getPrice().equals("0"))
                    adView.getPriceView().setVisibility(View.GONE);

            }
        }
        if (adView.getCallToActionView() != null) {
            if (unifiedNativeAd.getCallToAction() == null)
                adView.getCallToActionView().setVisibility(View.GONE);
            else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((TextView) adView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
        }
        adView.setNativeAd(unifiedNativeAd);
    }

}
