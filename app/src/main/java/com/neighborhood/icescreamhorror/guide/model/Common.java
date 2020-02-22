package com.neighborhood.icescreamhorror.guide.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.neighborhood.icescreamhorror.guide.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Common {
    public static ArrayList<Category> listCategory= new ArrayList<>();
    public static ArrayList<Question> listQuestion = new ArrayList<>();
    public static Bitmap loadBitmapImage(Context context,String name) throws IOException {
        AssetManager assetManager = context.getAssets();

        InputStream istr = assetManager.open(name);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }

    public static void setColorStatusBar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.my_statusbar_color));
        }
    }
}
