package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.GridImageAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class GridImageActivity extends AppCompatActivity {

    private ArrayList<String> imagesFromGallery;
    private ArrayList<String> selectedImageList;
    private Button sendImageBtn;
    private GridView gallery;
    GridImageAdapter gridImageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image);

        gallery = findViewById(R.id.galleryGridView);
        sendImageBtn = findViewById(R.id.sendBtnID);
        selectedImageList = new ArrayList<>();
        imagesFromGallery = getAllShownImagesPath(this);

        gridImageAdapter = new GridImageAdapter(this, imagesFromGallery, selectedImageList);
        gallery.setAdapter(gridImageAdapter);

        onClick();

    }

    private void onClick() {
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                if (null != imagesFromGallery && !imagesFromGallery.isEmpty()) {

                    // Logic to show button and show total selected image

                    if (selectedImageList.size() < 6) {
                        for (String image : selectedImageList) {
                            if (image.equalsIgnoreCase(imagesFromGallery.get(position))) {
                                selectedImageList.remove(image);
                                view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                                view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                                if (selectedImageList.size() == 0) {
                                    sendImageBtn.setVisibility(View.GONE);
                                } else {
                                    sendImageBtn.setText("Send (" + selectedImageList.size() + ")");
                                }

                                return;
                            }

                        }
                        selectedImageList.add(imagesFromGallery.get(position));
                        view.findViewById(R.id.textViewID).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.imageCheckIVId).setVisibility(View.VISIBLE);
                        sendImageBtn.setVisibility(View.VISIBLE);
                        sendImageBtn.setText("Send (" + selectedImageList.size() + ")");


                    } else {
                        for (String image : selectedImageList) {
                            if (image.equalsIgnoreCase(imagesFromGallery.get(position))) {
                                selectedImageList.remove(image);
                                view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                                view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                                sendImageBtn.setText("Send (" + selectedImageList.size() + ")");
                                return;
                            }

                        }
                        Toast.makeText(GridImageActivity.this, "Sorry ! You can not select more than 06 photos", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        sendImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putStringArrayListExtra("image", selectedImageList);
                setResult(4, intent);
                finish();
            }
        });
    }

    // Load all image from gallery

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);

        }
        Collections.reverse(listOfAllImages);
        return listOfAllImages;
    }
}
