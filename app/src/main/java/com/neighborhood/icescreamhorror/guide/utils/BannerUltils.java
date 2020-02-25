package com.neighborhood.icescreamhorror.guide.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Random;

public class BannerUltils {
    public static final int KEY_CONTRANS_ADS_BANNER_HOME = 0;
    public static final int KEY_CONTRANS_ADS_BANNER_ABOUT = 1;
    public static final int KEY_CONTRANS_ADS_BANNER_ANSWER = 2;
    public static final int KEY_CONTRANS_ADS_BANNER_MORE = 3;
    public static final int KEY_CONTRANS_ADS_BANNER_GUIDE_VIEW = 4;
    public static final int KEY_CONTRANS_ADS_BANNER_GUIDE_DETAIL = 5;
    public static final int KEY_CONTRANS_ADS_BANNER_QUESTION = 6;
    static final String TAG = "Banner Utils";
    private static String url = "https://raw.githubusercontent.com/vandoannguyen/json_guide_icecream/master/guide.json";

    public AdListener adListener(AdView adview, int keycheck) {
        return new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                int random = new Random().nextInt(100);
//                if (random <= 50) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            adview.setVisibility(View.GONE);
//                        }
//                    }, 500);
//
//                }
                OkHttpHandler okHttpHandler = new OkHttpHandler();
                okHttpHandler.setCallBackData(data -> {
                    Log.d(TAG, "onAdLoaded: " + data);
                    Log.d(TAG, "callBack: " + random);
                    if (random < data) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                }
                                            },
                                500);
                        adview.setVisibility(View.GONE);
                    }
                });
                okHttpHandler.execute(new String[]{url});
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        };
    }

    private class OkHttpHandler extends AsyncTask<String, Void, Integer> {
        private CallBackData callBackData;
        private OkHttpClient client = new OkHttpClient();
//        Context c;


        public CallBackData getCallBackData() {
            return callBackData;
        }

        public void setCallBackData(CallBackData callBackData) {
            this.callBackData = callBackData;
        }

        @Override
        protected Integer doInBackground(String... params) {
            Log.d(TAG, "doInBackground: " + "start");
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            int a = 50;
            try {
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                if (data != null) {
                    Person person = new Gson().fromJson(data, Person.class);
                    a = person.getAdPerson();
                }
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: ");
//                Toast.makeText(c, "Connect failed", Toast.LENGTH_SHORT).show();
            }
            return a;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            this.callBackData.callBack(integer);
            Log.d(TAG, "onPostExecute: " + integer);
        }
    }

    protected interface CallBackData {
        void callBack(int data);
    }

    protected class Person {

        @SerializedName("ad_person")
        @Expose
        private Integer adPerson;

        public Integer getAdPerson() {
            return adPerson;
        }

        public void setAdPerson(Integer adPerson) {
            this.adPerson = adPerson;
        }

    }
}
