package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.util.ReadExternalStoragePermission;
import com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ReadExternalStoragePermission.isReadStoragePermissionGranted(HomeActivity.this);
    }

    public void openGallery(View view) {
        startActivity(new Intent(HomeActivity.this, SelectFromGalleryActivity.class));
    }

    public void openGalleryLikeFacebook(View view) {

        startActivity(new Intent(HomeActivity.this, SelectFromGalleryLikeFacebookActivity.class));
        ReadExternalStoragePermission.isReadStoragePermissionGranted(HomeActivity.this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                break;

        }
    }
}
