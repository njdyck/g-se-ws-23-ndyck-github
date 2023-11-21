package com.gse23.ndyck.model;

import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;
import java.io.InputStream;

  public final class ExifReader {
      InputStream inputStream;

      ExifReader(InputStream inputStream) {
          this.inputStream = inputStream;
      }

    public static ImageInformation readExif(InputStream in) throws IOException {
        ExifInterface exifInterface = new ExifInterface(in);
        String longitude = exifInterface.
                getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String latitude = exifInterface.
                getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String discribtion = exifInterface.
                getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION);

        return new ImageInformation(longitude, latitude, discribtion);
    }
}
