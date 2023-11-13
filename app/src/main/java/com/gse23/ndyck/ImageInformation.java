package com.gse23.ndyck;

public class ImageInformation {

    public ImageInformation(String artist, String longitude, String latitude){
        this.artist = artist;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getArtist() {
        return artist;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() { return latitude; }
    private final String artist;
    private final String longitude;
    private final String latitude;
}
