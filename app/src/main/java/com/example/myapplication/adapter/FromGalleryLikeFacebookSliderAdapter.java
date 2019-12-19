package com.example.myapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import java.util.List;

public class FromGalleryLikeFacebookSliderAdapter extends PagerAdapter {

    private Context context;
    private List<String> images;

    public FromGalleryLikeFacebookSliderAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView;

        View view = LayoutInflater.from(context).inflate(R.layout.slider_item, container, false);

        imageView = view.findViewById(R.id.slider_image_view);
        Glide.with(context).load(images.get(position)).into(imageView);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
