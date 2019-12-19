package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;

public class GridImageAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<String> images;
    private ArrayList<String> imageList;


    public GridImageAdapter(Activity localContext, ArrayList<String> images, ArrayList<String> imageList) {
        context = localContext;
        this.images = images;
        this.imageList = imageList;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {

        String image = images.get(position);
        ImageView imageView;


        View view = LayoutInflater.from(context).inflate(R.layout.layout_gridview, parent, false);

        imageView = view.findViewById(R.id.slider_image_view);

        for (String s : imageList) {
            if (s.equalsIgnoreCase(images.get(position))) {
                view.findViewById(R.id.textViewID).setVisibility(View.VISIBLE);
                view.findViewById(R.id.imageCheckIVId).setVisibility(View.VISIBLE);
            }
        }

        Glide.with(context).load(image)
                .placeholder(R.drawable.ic_gallery).centerCrop()
                .into(imageView);

        return view;
    }
}
