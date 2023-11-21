package com.gse23.ndyck.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

        getPicInfo(map);
        logFileInfos(names);
        displayRandomImage();
    }

    private void displayRandomImage() {
        String randomFileName = getRandomFileName(names);

        if (randomFileName != null && !displayedImages.contains(randomFileName)) {
            getImage(randomFileName);
            displayedImages.add(randomFileName);
        } else {
            if (displayedImages.size() == names.size()) {
                Snackbar.make(findViewById(android.R.id.content),
                        "Alle Bilder wurden angezeigt", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    public void getImage(String fileName) {
        try {
            InputStream in = getAssets().open("albums/" + map + "/" + fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // gets the cords of a file
    public List<String> getExifs(String fileName) {
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

    public void nextPic(View view) {
        displayRandomImage();
    }

    public String getRandomFileName(HashMap<String, List<String>> names) {
        List<String> fileNames = new ArrayList<>(names.keySet());

        if (!fileNames.isEmpty()) {
            int randomIndex = (int) (Math.random() * fileNames.size());
            return fileNames.get(randomIndex);
        } else {
            return null;
        }
    }
}