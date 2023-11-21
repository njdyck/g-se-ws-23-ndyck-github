package com.gse23.ndyck.model;

public class ImageInformation {

    private final String longitude;
    private final String latitude;

    public ImageInformation(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
