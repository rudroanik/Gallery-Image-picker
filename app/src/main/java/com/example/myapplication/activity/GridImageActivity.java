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

    private ArrayList<String> images;
    private ArrayList<String> imageList;
    private Button sendImageBtn;
    GridImageAdapter gridImageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_image);

        GridView gallery = findViewById(R.id.galleryGridView);
        sendImageBtn = findViewById(R.id.sendBtnID);
        imageList = new ArrayList<>();
        images = getAllShownImagesPath(this);

        gridImageAdapter = new GridImageAdapter(this, images, imageList);
        gallery.setAdapter(gridImageAdapter);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {

                if (null != images && !images.isEmpty()) {


                    if (imageList.size() < 6) {
                        for (String image : imageList) {
                            if (image.equalsIgnoreCase(images.get(position))) {
                                imageList.remove(image);
                                view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                                view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                                if (imageList.size() == 0) {
                                    sendImageBtn.setVisibility(View.GONE);
                                } else {
                                    sendImageBtn.setText("Send (" + imageList.size() + ")");
                                }

                                return;
                            }

                        }
                        imageList.add(images.get(position));
                        view.findViewById(R.id.textViewID).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.imageCheckIVId).setVisibility(View.VISIBLE);
                        sendImageBtn.setVisibility(View.VISIBLE);
                        sendImageBtn.setText("Send (" + imageList.size() + ")");


                    } else {
                        for (String s : imageList) {
                            if (s.equalsIgnoreCase(images.get(position))) {
                                imageList.remove(s);
                                view.findViewById(R.id.textViewID).setVisibility(View.GONE);
                                view.findViewById(R.id.imageCheckIVId).setVisibility(View.GONE);
                                sendImageBtn.setText("Send (" + imageList.size() + ")");
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
                intent.putStringArrayListExtra("image", imageList);
                setResult(4, intent);
                finish();
            }
        });

    }


    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);

        }
        Collections.reverse(listOfAllImages);
        return listOfAllImages;
    }
}
