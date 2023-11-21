package com.gse23.ndyck.model;

public class noMapSelectedException extends Exception {
    public noMapSelectedException() {
        super("Es wurden keine Bilder im ausgewählten Album gefunden.");
    }
}
