package com.example.iscream2_android.guide;

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

import com.example.iscream2_android.R;
import com.example.iscream2_android.model.Category;
import com.example.iscream2_android.model.Common;
import com.example.iscream2_android.utils.UnifiedNativeAdsUtils;
import com.google.android.gms.ads.AdListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

public class GuideItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ADS_TYPE = 0;
    private static final int NOMAL_TYPE = 2;
    ArrayList<Category> listCategory;
    Activity activity;

    public GuideItemAdapter(ArrayList<Category> listCategory, Activity activity) {
        this.listCategory = listCategory;
        listCategory.add(3,new Category());
        listCategory.add(new Category());
        this.activity = activity;
    }

    private ClickItemListener clickItemListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ADS_TYPE) {
            View view = inflater.inflate(R.layout.item_ads, parent, false);
            return new AdsViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_view_guide, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (listCategory.get(position).equals(new Category())) {
            ((AdsViewHolder) holder).frameLayout.removeAllViews();
            UnifiedNativeAdsUtils.getInstance(activity).setNativeAds(((AdsViewHolder) holder).frameLayout, R.layout.ad_unified_draw_navigator, new AdListener() {
            });
        } else {
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickItemListener != null){
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

    }

    @Override
    public int getItemViewType(int position) {
        return listCategory.get(position).equals(new Category()) ? ADS_TYPE : NOMAL_TYPE;
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

    public class AdsViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frameItemAds);

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
