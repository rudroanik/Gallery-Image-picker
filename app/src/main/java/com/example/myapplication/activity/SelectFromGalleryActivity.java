package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.FromGallerySliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SelectFromGalleryActivity extends AppCompatActivity {

    private ImageView imageView;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<Uri> uriList;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_gallery);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle);
        imageView = findViewById(R.id.imageViewID);
        uriList = new ArrayList<>();


    }

    public void openGallery(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), 1);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ClipData clipData = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                clipData = data.getClipData();
            }

            if (clipData != null) {


                if (clipData.getItemCount() > 6) {

                    Toast.makeText(this, "Sorry! You can not select more than 6 photos", Toast.LENGTH_SHORT).show();

                } else {
                    uriList.clear();
                    viewPager.clearOnPageChangeListeners();

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        uriList.add(clipData.getItemAt(i).getUri());
                    }

                    viewPager.setAdapter(new FromGallerySliderAdapter(this, uriList));
                    viewPager.setVisibility(View.VISIBLE);
                    circleIndicator.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    circleIndicator.setViewPager(viewPager);
                    getDelayToSwipeViewpager();
                }


            } else {
                Uri uri = data.getData();

                viewPager.setVisibility(View.GONE);
                circleIndicator.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(uri).into(imageView);
            }

        }
    }

    private void getDelayToSwipeViewpager() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == uriList.size()) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }


}
