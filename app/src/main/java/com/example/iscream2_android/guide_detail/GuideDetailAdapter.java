package com.example.iscream2_android.guide_detail;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iscream2_android.R;
import com.example.iscream2_android.model.Common;

import java.io.IOException;
import java.util.ArrayList;

public class GuideDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> list;
    static final int IMAGE = 1, TEXT = 2;

    public GuideDetailAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == IMAGE) {
            View view = inflater.inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_text_guide, parent, false);
            return new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.get(position).contains(".png")){
            Bitmap bitmap = null;
            try {
                bitmap = Common.loadBitmapImage(holder.itemView.getContext(),"i"+list.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((ImageViewHolder)holder).imgItem.setImageBitmap(bitmap);
        }
        else {
            ((TextViewHolder)holder).txtItem.setText(list.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).contains(".png") ? IMAGE : TEXT;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        TextView txtItem;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);
        }
    }
}
