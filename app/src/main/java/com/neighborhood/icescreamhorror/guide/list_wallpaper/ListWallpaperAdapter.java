package com.neighborhood.icescreamhorror.guide.list_wallpaper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.model.WallpaperModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListWallpaperAdapter extends RecyclerView.Adapter<ListWallpaperAdapter.ViewHolder> {
    OnItemClick onItemClick;
    private ArrayList<WallpaperModel> listWallpaper;

    public ListWallpaperAdapter(ArrayList<WallpaperModel> listWallpaper) {
        this.listWallpaper = listWallpaper;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_wallpaper,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position%2==0)
            holder.imgView.setMaxHeight(300);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!= null)
                    onItemClick.onClick(position);
            }
        });
        Picasso.with(holder.itemView.getContext())
                .load(listWallpaper.get(position).getLarge())
                .centerInside()
                .resize(1080,1920)
                .into(holder.imgView);
//        holder.imgView.setImageBitmap(getBitmapFromAsset(holder.imgView.getContext(), position+".png"));
    }

    @Override
    public int getItemCount() {
        return listWallpaper!= null ? listWallpaper.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView= itemView.findViewById(R.id.imageViewWallPaper);
        }
    }
    public interface OnItemClick{
        void onClick(int index);
    }
    private Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }
        return bitmap;
    }

}
