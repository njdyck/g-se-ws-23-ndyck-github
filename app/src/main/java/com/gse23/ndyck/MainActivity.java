package com.gse23.ndyck;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAlbums();
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
                        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                                || fileName.endsWith(".png")) {
                            Log.i("Albumname:", albumName);
                            Log.i("Dateiname:", fileName);

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
}

