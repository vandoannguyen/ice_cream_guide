package com.neighborhood.icescreamhorror.guide.list_wallpaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.WallpaperView;
import com.neighborhood.icescreamhorror.guide.model.Category;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.model.Question;
import com.neighborhood.icescreamhorror.guide.model.WallpaperModel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListWallpaper extends AppCompatActivity {

    @BindView(R.id.imgBackListWallpaper)
    ImageView imgBackListWallpaper;
    @BindView(R.id.revListWallpaper)
    RecyclerView revListWallpaper;
    private ArrayList<WallpaperModel> listWallpaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listWallpaper = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);
        getDataFromJson(this);
        ButterKnife.bind(this);
        Common.setColorStatusBar(this);
        initView();
    }

    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        revListWallpaper.setLayoutManager(manager);
        ListWallpaperAdapter adapter = new ListWallpaperAdapter(listWallpaper);
        revListWallpaper.setHasFixedSize(true);
        revListWallpaper.setItemViewCacheSize(20);
        adapter.setOnItemClick(new ListWallpaperAdapter.OnItemClick() {
            @Override
            public void onClick(int index) {
                Intent intent = new Intent(ListWallpaper.this, WallpaperView.class);
                intent.putExtra("index", listWallpaper.get(index));
                startActivity(intent);
            }
        });
        revListWallpaper.setAdapter(adapter);

    }

    @OnClick(R.id.imgBackListWallpaper)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getDataFromJson(Context context) {
        Type type1 = new TypeToken<ArrayList<WallpaperModel>>() {
        }.getType();
        listWallpaper = new Gson().fromJson(loadJSONFromAsset(this, "wallpaper"), type1);
        Log.e("getDataFromJson: ", loadJSONFromAsset(this, "data"));
        Log.e("getDataFromJson: ", listWallpaper.size() + "");
    }

    public String loadJSONFromAsset(Context context, String name) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(name + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
