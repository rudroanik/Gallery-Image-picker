package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.util.ReadExternalStoragePermission;
import com.example.myapplication.R;
import com.example.myapplication.adapter.FromGalleryLikeFacebookSliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SelectFromGalleryLikeFacebookActivity extends AppCompatActivity {
    private ImageView imageView;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 600;
    final long PERIOD_MS = 2000;
    private List<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_gallery_like_facebook);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle);
        imageView = findViewById(R.id.imageViewID);
    }

    public void openGallery(View view) {
        if (ReadExternalStoragePermission.isReadStoragePermissionGranted(SelectFromGalleryLikeFacebookActivity.this)) {
            startActivityForResult(new Intent(SelectFromGalleryLikeFacebookActivity.this, GridImageActivity.class), 4);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 4) {

            imageList.clear();
            imageList = data.getStringArrayListExtra("image");

            viewPager.setAdapter(new FromGalleryLikeFacebookSliderAdapter(this, imageList));
            viewPager.setVisibility(View.VISIBLE);
            circleIndicator.setVisibility(View.VISIBLE);
            circleIndicator.setViewPager(viewPager);
            getDelayToSwipeViewpager();
        }
    }

    private void getDelayToSwipeViewpager() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageList.size()) {
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
