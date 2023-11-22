package com.gse23.ndyck.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
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
    EditText inputLon;
    EditText inputLat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = findViewById(R.id.imageView);
        map = getIntent().getStringExtra("Ausgewählte Map: ");
        Log.i("Ausgewählte Map: ", map);

        inputLat = (EditText) findViewById(R.id.inputLat);
        inputLon = (EditText) findViewById(R.id.inputLon);

        names = Image.getPicInfo(this, map);
        Image.logFileInfos(names);
        Image.displayRandomImage(this, map, imageView, names, displayedImages);

    }

    public void nextPic(View view) {
        Image.displayRandomImage(this, map, imageView, names, displayedImages);
        inputLon.setText("");
        inputLat.setText("");
    }
    public void guess(View view) {
        double lat = Double.parseDouble(String.valueOf(inputLat.getText()));
        double lon = Double.parseDouble(String.valueOf(inputLon.getText()));

        if (lon < -180 || lon > 180) {
            Snackbar.make(view, "Der Wertebereich für Längengerade liegt bei: {-180,180}",
                    Snackbar.LENGTH_LONG).show();
        } if (lat < -90 || lat > 90) {
            Snackbar.make(view, "Der Wertebereich für Breitengrade liegt bei: {-90,90}",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}