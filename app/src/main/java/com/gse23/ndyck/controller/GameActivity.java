package com.gse23.ndyck.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.gse23.ndyck.R;
import com.gse23.ndyck.model.CorruptedExifDataException;
import com.gse23.ndyck.model.ExifReader;
import com.gse23.ndyck.model.ImageInformation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends MainActivity {
    HashMap<String, List<String>> names = new HashMap<String,List<String>>();
    String map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        map = getIntent().getStringExtra("Ausgewählte Map: ");
        Log.i("Ausgewählte Map: ", map);

        getPicInfo(map);

        logFileInfos(names);


    }

    // gets the cords of a file
    private List<String> getExifs(String fileName) {
        ImageInformation infos =
                readExif("albums/" + map+ "/" + fileName);

        List<String> exifs = new ArrayList<>();

        String lat = infos.getLatitude();
        String lon = infos.getLongitude();
        String dis = infos.getDiscribtion();
        exifs.add(lat);
        exifs.add(lon);
        exifs.add(dis);

        return exifs;
    }

    // creates a Hashmap with the name of a file in the chosen album and its cords
     HashMap<String, List<String>> getPicInfo(String map) {
        String album = "albums/";
        AssetManager assetManager = getAssets();
        try {
            String[] files = assetManager.list(album + map);
            if (files != null) {
                for (String fileName : files) {
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                            || fileName.endsWith(".png")) {
                        names.put(fileName, new ArrayList<>(getExifs(fileName)));
                        }
                    }
                }
        } catch (IOException e) {
            try {
                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                startActivity(intent);
                throw new CorruptedExifDataException();
            } catch (CorruptedExifDataException ex) {
                throw new RuntimeException(ex);
            }
        }
        return names;
    }

    public ImageInformation readExif(String filepath) {
        try (InputStream in = getAssets().open(filepath)) {
            return ExifReader.readExif(in);
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "Bild konnte nicht geladen werden!", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return null;
        }
    }

    public void logFileInfos(HashMap<String, List<String>> names){
        for (String fileName : names.keySet()) {
            List<String> infos = names.get(fileName);

            String lat = infos.get(0);
            String lon = infos.get(1);
            String dis = infos.get(2);

            Log.i("FileName: " + fileName, "Latitude: " + lat + ", Longitude: " + lon +
                    ", Discribtion: " + dis);
        }

    }

}