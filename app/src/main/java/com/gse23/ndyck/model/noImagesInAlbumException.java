package com.gse23.ndyck.model;

public class noImagesInAlbumException extends Exception {
    public noImagesInAlbumException() {
        super("Es wurden keine Bilder im ausgewählten Album gefunden.");
    }
}
