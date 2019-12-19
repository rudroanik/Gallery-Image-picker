package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FromGallerySliderAdapter extends PagerAdapter {

    private Context context;
    private List<Uri> images;

    public FromGallerySliderAdapter(Context context, List<Uri> images) {
        this.context = context;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView;

        View view = LayoutInflater.from(context).inflate(R.layout.slider_item, container, false);

        imageView = view.findViewById(R.id.slider_image_view);
        Glide.with(context).load(images.get(position)).into(imageView);
        container.addView(view);


        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
