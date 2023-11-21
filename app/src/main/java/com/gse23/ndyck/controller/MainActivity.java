package com.gse23.ndyck.controller;


import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.gse23.ndyck.R;
import com.gse23.ndyck.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List items = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAlbums();

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                items
        );

        autoCompleteTextView.setAdapter(adapter);
    }
    private void checkAlbums() {
        String album = "albums/";
        AssetManager assetManager = getAssets();
        try {
            String[] albums = assetManager.list(album);
            for (String albumName: albums) {
                String[] files = assetManager.list(album + albumName);
                if (files != null) {
                    for (String fileName : files) {
                        if (isImageFile(fileName)) {
                            Log.i("Albumname:", albumName);
                            Log.i("Dateiname:", fileName);
                            if (!items.contains(albumName)) {
                                items.add(albumName);
                            }
                            String filePath = album + albumName + "/" + fileName;

                            ImageInformation infos =
                                    readExif(filePath);

                            String latitude = infos.getLatitude();
                            String longitude = infos.getLongitude();

                            Log.i(album, String.valueOf(latitude));
                            Log.i(album, String.valueOf(longitude));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("ERROR:", "Fehler beim Lesen der Dateien");
        }
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

    private boolean isImageFile(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }

    public void onClick(View view) {
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        String map = autoCompleteTextView.getText().toString();
        intent.putExtra("Ausgewählte Map: ", map);

        if (items.contains(map)) {
            startActivity(intent);
        } else {
            try {
                throw new noImagesInAlbumException();
            } catch (noImagesInAlbumException e) {
                e.printStackTrace();
                Snackbar.make(view, "Keine Bilder in dem Album", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}

