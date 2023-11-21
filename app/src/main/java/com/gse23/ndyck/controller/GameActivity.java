package com.gse23.ndyck.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gse23.ndyck.R;
import com.gse23.ndyck.view.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends MainActivity {
    private ImageView imageView;
    HashMap<String, List<String>> names = new HashMap<String,List<String>>();
    String map;
    private List<String> displayedImages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = findViewById(R.id.imageView);
        map = getIntent().getStringExtra("Ausgewählte Map: ");
        Log.i("Ausgewählte Map: ", map);

        names = Image.getPicInfo(this, map);
        Image.logFileInfos(names);
        Image.displayRandomImage(this, map, imageView, names, displayedImages);

    }

    public void nextPic(View view) {
        Image.displayRandomImage(this, map, imageView, names, displayedImages);
    }
}