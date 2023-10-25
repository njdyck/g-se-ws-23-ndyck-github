package com.gse23.ndyck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAlbums("Italien");
        checkAlbums("Urlaub Österreich");
        checkAlbums("Ostwestfalen");
    }

    public static void checkAlbums1(File[] album){
        if (album != null) {
            for (File land_album : album) {
                String albumName = land_album.getName();
                File[] land = land_album.listFiles();

                if (land != null) {
                    for (File land_index : land) {
                        if (land_index.isFile() && (
                                land_index.getName().endsWith(".jpg") ||
                                        land_index.getName().endsWith(".jpeg") ||
                                        land_index.getName().endsWith(".png")
                        )) {
                            Log.i("Albumname: ", albumName);
                            Log.i("Dateiname: ", land_index.getName());
                        }
                    }
                }
            }
        } else {
            Log.i("ERROR:", "Keine entsprechenden Dateien");
        }
    }
    private void checkAlbums(String albumName) {
        AssetManager assetManager = getAssets();
        try {
            String[] files = assetManager.list("albums/" + albumName);

            for (String fileName : files) {
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                    Log.i("Albumname: ", albumName);
                    Log.i("Dateiname: ", fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("ERROR:", "Keine entsprechenden Dateien im Album " + albumName);
        }
    }

}

