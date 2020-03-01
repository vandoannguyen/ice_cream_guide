package com.neighborhood.icescreamhorror.guide;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.model.WallpaperModel;
import com.neighborhood.icescreamhorror.guide.utils.CustomDialogSetWallpaper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WallpaperView extends AppCompatActivity {

    private static final int WALLPAPER_REQUEST_CODE = 0;
    private static final int WALLPAPER_REQUEST_STOGARE_CODE = 1;
    private static final String TAG = "WallpaperView";

    @BindView(R.id.imgBackWallpaper)
    ImageView imgBackWallpaper;
    @BindView(R.id.imgSetWallpaper)
    ImageView imgSetWallpaper;
    @BindView(R.id.imgWallpaper)
    ImageView imgWallpaper;

    CustomDialogSetWallpaper dialogSetWallpaper;
    private WallpaperModel imageData = null;
    private PublisherInterstitialAd mPublisherInterstitialAd;

    private final String SYSTEM_WALLPAPER = "system";
    private final String LOCK_WALLPAPER = "lock";
    private final String BOTH_WALLPAPER = "both";
    private String imagePathFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wapper_view);
        Common.setColorStatusBar(this);
        Intent intent = getIntent();
        imageData = (WallpaperModel) intent.getSerializableExtra("index");
        initAds();
        ButterKnife.bind(this);
        Picasso.with(this)
                .load(imageData.getLarge())
                .centerInside()
                .resize(1080, 1920)
                .into(imgWallpaper);
//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(index + ".png"));
//            imgWallpaper.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.ads_id_app));
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId(getString(R.string.id_interstitial_ad));
        dialogSetWallpaper = new CustomDialogSetWallpaper(this);
        dialogSetWallpaper.setNativeAds(getString(R.string.id_native_ads));

    }

    @OnClick({R.id.imgBackWallpaper, R.id.imgSetWallpaper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBackWallpaper:
                onBackPressed();
                break;
            case R.id.imgSetWallpaper: {
                Toast.makeText(this, "asdkjalskdjasd", Toast.LENGTH_SHORT).show();
                permissionDownloadImage(imageData.getLarge());
                break;
            }

        }
    }

    private void requestWallpaperPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SET_WALLPAPER, Manifest.permission.SET_WALLPAPER_HINTS}, WALLPAPER_REQUEST_CODE);
    }

    private boolean checkPermissionWallpaper() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WALLPAPER_REQUEST_CODE) {
            if (checkPermissionWallpaper()) {
                try {
                    if (!imagePathFile.equals("")) {
                        setImageWallpaper(imagePathFile);
                    } else
                        Toast.makeText(this, "Can not set wallpaper!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == WALLPAPER_REQUEST_STOGARE_CODE) {
            if (checkStogarePermission()) {
                imagePathFile = saveImage(imageData.getLarge());
                if (!imagePathFile.equals("")) {
                    if (!imagePathFile.equals("")) {
                        try {
                            setImageWallpaper(imagePathFile);
                        } catch (Exception e) {
                            Toast.makeText(this, "Can not set wallpaper!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void setImageWallpaper(String path) throws Exception {

        dialogSetWallpaper.setNativeAds(getString(R.string.id_native_ads));
        dialogSetWallpaper.setOnButtonClickListener(new CustomDialogSetWallpaper.OnButtonClickListener() {
            @Override
            public void system() {
                showInter();
                setWallpaper(SYSTEM_WALLPAPER, path);
            }

            @Override
            public void lock() {
                showInter();
                setWallpaper(LOCK_WALLPAPER, path);
            }

            @Override
            public void both() {
                showInter();
                setWallpaper(BOTH_WALLPAPER, path);
            }
        });
        dialogSetWallpaper.show();
    }

    private void showInter() {
        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mPublisherInterstitialAd.show();
            }
        });
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
    }

    void permissionDownloadImage(String link) {
        if (!checkStogarePermission()) {
            requestStogarePermisison();
        } else {
            imagePathFile = saveImage(imageData.getLarge());
            Log.d(TAG, "permissionDownloadImage: " + imagePathFile);
            if (imagePathFile.equals("")) {
                Toast.makeText(this, "Cant't save image!!", Toast.LENGTH_SHORT).show();
            } else {
                if (checkPermissionWallpaper()) {
                    try {
                        setImageWallpaper(imagePathFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Can't set Wallpaper!", Toast.LENGTH_SHORT).show();
                    }
                } else requestWallpaperPermission();
            }
        }
    }

    private boolean checkStogarePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStogarePermisison() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, WALLPAPER_REQUEST_STOGARE_CODE);
    }

    private void setWallpaper(String value, String path) {
        Toast.makeText(this, "Setting Wallpaper ...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "setWallpaper: " + path);
        WallpaperManager manager = WallpaperManager.getInstance(this);
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voidd) {
                boolean isWallpaperSette = false;

                Bitmap bitmap = null;
                try {
//                    File initialFile = new File(path);
                    InputStream targetStream = new FileInputStream(path);
                    bitmap = BitmapFactory.decodeStream(targetStream);
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: "+e.getMessage() );
                    isWallpaperSette = false;
                    e.printStackTrace();
                }
                switch (value) {
                    case SYSTEM_WALLPAPER: {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                                isWallpaperSette = true;
                            } else {
                                isWallpaperSette = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        } finally {
                            break;
                        }
                    }
                    case LOCK_WALLPAPER: {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                                isWallpaperSette = true;
                            } else {
                                isWallpaperSette = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            isWallpaperSette = false;
                        } finally {
                            break;
                        }
                    }
                    case BOTH_WALLPAPER: {
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                                isWallpaperSette = true;
                            } else {
                                isWallpaperSette = false;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            isWallpaperSette = false;
                        } finally {
                            break;
                        }
                    }
                }
                return isWallpaperSette;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    Toast.makeText(WallpaperView.this, "Set Wallpaper is successful", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(WallpaperView.this, "Can't set Wallpaper!", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public String saveImage(String imageUrl) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + getPackageName();
//        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String folderPath = root + "/wallpaper/";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folderPath + "/myImage.png");
        Toast.makeText(this, "Download image ...", Toast.LENGTH_SHORT).show();
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(imageUrl);
            is = url.openStream();
            OutputStream os = null;
            os = new FileOutputStream(file,false);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Can't download image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            Toast.makeText(this, "Can't download image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return "";
        }
//        return "";
    }
}
