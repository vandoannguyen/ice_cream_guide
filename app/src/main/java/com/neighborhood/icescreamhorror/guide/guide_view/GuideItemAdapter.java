package com.neighborhood.icescreamhorror.guide.guide_view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neighborhood.icescreamhorror.guide.R;
import com.neighborhood.icescreamhorror.guide.model.Category;
import com.neighborhood.icescreamhorror.guide.model.Common;
import com.neighborhood.icescreamhorror.guide.utils.UnifiedNativeAdsUtils;
import com.google.android.gms.ads.AdListener;

import java.io.IOException;
import java.util.ArrayList;

public class GuideItemAdapter extends RecyclerView.Adapter<GuideItemAdapter.ViewHolder> {
    private static final int ADS_TYPE = 0;
    private static final int NOMAL_TYPE = 2;
    ArrayList<Category> listCategory;
    Activity activity;

    public GuideItemAdapter(ArrayList<Category> listCategory, Activity activity) {
        this.listCategory = listCategory;
        this.activity = activity;
    }

    private ClickItemListener clickItemListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    @NonNull
    @Override
    public GuideItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_view_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideItemAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null) {
                    clickItemListener.onClick(position);
                }

            }
        });

        ((ViewHolder) holder).txtLable.setText(listCategory.get(position).getName());
        int resourceImage = R.drawable.background;
        Bitmap bitmap = null;
        try {
            bitmap = Common.loadBitmapImage(holder.itemView.getContext(), "i" + listCategory.get(position).getImage());
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        if (bitmap != null) {
            ((ViewHolder) holder).imgGuide.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLable;
        ImageView imgGuide;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLable = itemView.findViewById(R.id.txtLable);
            imgGuide = itemView.findViewById(R.id.imgGuide);
        }
    }
    public interface ClickItemListener {
        void onClick(int index);
    }

    private int getResources(Context context, String name) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resourceId;
    }
}
