package com.gse23.ndyck.model;

public class ImageInformation {

    private final String longitude;
    private final String latitude;
    private final String discribtion;

    public ImageInformation(String longitude, String latitude, String discribtion) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.discribtion = discribtion;
    }
    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDiscribtion() {
        return discribtion;
    }

}
