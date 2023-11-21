package com.gse23.ndyck.model;

public class CorruptedExifDataException extends Throwable {
    public CorruptedExifDataException() {
        super("Fehler beim Lesen der Exif-Daten");
    }
}
