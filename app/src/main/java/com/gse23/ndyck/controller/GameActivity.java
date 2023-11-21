package com.gse23.ndyck.controller;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.gse23.ndyck.R;
import com.gse23.ndyck.model.noMapSelectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String map = getIntent().getStringExtra("Ausgewählte Map: ");
        Log.i("Ausgewählte Map: ", map);

        List names = getPicNames(map);

    }

    List getPicNames(String map) {
        String album = "albums/";
        List names = new ArrayList();
        AssetManager assetManager = getAssets();
        try {
            String[] files = assetManager.list(album + map);
            if (files != null) {
                for (String fileName : files) {
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                            || fileName.endsWith(".png")) {
                        names.add(fileName);
                        }
                    }
                }
        } catch (IOException e) {
            try {
                throw new noMapSelectedException();
            } catch (noMapSelectedException ex) {
                throw new RuntimeException(ex);
            }
        }
        return names;
    }

}