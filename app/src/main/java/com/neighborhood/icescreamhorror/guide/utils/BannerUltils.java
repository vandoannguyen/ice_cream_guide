package com.neighborhood.icescreamhorror.guide.utils;

import com.google.android.gms.ads.AdListener;

import java.util.Random;

public class BannerUltils {
    public static final int KEY_CONTRANS_ADS_BANNER_HOME = 0;
    public static final int KEY_CONTRANS_ADS_BANNER_ABOUT = 1;
    public static final int KEY_CONTRANS_ADS_BANNER_ANSWER = 2;
    public static final int KEY_CONTRANS_ADS_BANNER_MORE = 3;
    public static final int KEY_CONTRANS_ADS_BANNER_GUIDE_VIEW = 4;
    public static final int KEY_CONTRANS_ADS_BANNER_GUIDE_DETAIL = 5;
    public static final int KEY_CONTRANS_ADS_BANNER_QUESTION = 6;

    public AdListener adListener(int keycheck) {
        return new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                int random = new Random().nextInt(100);
//               super.onAdLoaded();
                switch (keycheck) {
                    case KEY_CONTRANS_ADS_BANNER_HOME: {
                        if (random < 50) {
                            super.onAdClicked();
                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_ABOUT: {
                        if (random < 50) {

                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_ANSWER: {
                        if (random < 50) {

                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_MORE: {
                        if (random < 50) {

                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_GUIDE_VIEW: {
                        if (random < 50) {

                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_GUIDE_DETAIL: {
                        if (random < 50) {

                        }
                        break;
                    }
                    case KEY_CONTRANS_ADS_BANNER_QUESTION: {
                        if (random < 50) {

                        }
                        break;
                    }
                }
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        };
    }
}
