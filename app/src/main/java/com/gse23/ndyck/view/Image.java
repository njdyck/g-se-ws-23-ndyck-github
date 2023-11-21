package com.gse23.ndyck.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.gse23.ndyck.controller.GameActivity;
import com.gse23.ndyck.model.CorruptedExifDataException;
import com.gse23.ndyck.model.ExifReader;
import com.gse23.ndyck.model.ImageInformation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Image {

    public static HashMap<String, List<String>> getPicInfo(Context context, String map) {
        HashMap<String, List<String>> names = new HashMap<>();
        String album = "albums/";
        AssetManager assetManager = context.getAssets();

        try {
            String[] files = assetManager.list(album + map);
            if (files != null) {
                for (String fileName : files) {
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                            || fileName.endsWith(".png")) {
                        names.put(fileName, new ArrayList<>(getExifs(context, map, fileName)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(new CorruptedExifDataException());
        }

        return names;
    }

    public static List<String> getExifs(Context context, String map, String fileName) {
        ImageInformation infos = readExif(context, "albums/" + map + "/" + fileName);

        List<String> exifs = new ArrayList<>();

        String lat = infos.getLatitude();
        String lon = infos.getLongitude();
        String dis = infos.getDiscribtion();
        exifs.add(lat);
        exifs.add(lon);
        exifs.add(dis);

        return exifs;
    }

    public static ImageInformation readExif(Context context, String filepath) {
        try (InputStream in = context.getAssets().open(filepath)) {
            return ExifReader.readExif(in);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(new CorruptedExifDataException());
        }
    }

    public static void logFileInfos(HashMap<String, List<String>> names) {
        for (String fileName : names.keySet()) {
            List<String> infos = names.get(fileName);

            String lat = infos.get(0);
            String lon = infos.get(1);
            String dis = infos.get(2);

            Log.i("FileName: " + fileName, "Latitude: " + lat + ", Longitude: " + lon +
                    ", Discribtion: " + dis);
        }
    }

    public static void displayRandomImage(GameActivity activity, String map, ImageView imageView,
                                          HashMap<String, List<String>> names,
                                          List<String> displayedImages) {
        String randomFileName = getRandomFileName(names, displayedImages);

        if (randomFileName != null && !displayedImages.contains(randomFileName)) {
            displayImage(activity, map, randomFileName, imageView);
            displayedImages.add(randomFileName);
        } else {
            if (displayedImages.size() == names.size()) {
                Snackbar.make(
                        activity.findViewById(android.R.id.content),
                        "Alle Bilder wurden angezeigt",
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        }
    }

    public static void displayImage(Context context, String map,
                                    String fileName, ImageView imageView) {
        try {
            InputStream in = context.getAssets().open("albums/" + map + "/" + fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String getRandomFileName(HashMap<String, List<String>> names,
                                           List<String> displayedImages) {
        List<String> fileNames = new ArrayList<>(names.keySet());

        if (!fileNames.isEmpty()) {
            int randomIndex = (int) (Math.random() * fileNames.size());
            return fileNames.get(randomIndex);
        } else {
            return null;
        }
    }
}
