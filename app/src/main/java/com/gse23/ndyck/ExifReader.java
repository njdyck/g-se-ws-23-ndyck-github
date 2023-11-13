package com.gse23.ndyck;

import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;
import java.io.InputStream;

  public class ExifReader {
      private InputStream inputStream;

      private ExifReader(InputStream inputStream) {
          this.inputStream = inputStream;
      }
    public static ImageInformation readExif(InputStream in) throws IOException {
        String name = null;
        ExifInterface exifInterface = new ExifInterface(in);
        name = exifInterface.getAttribute(ExifInterface.TAG_ARTIST);
        String longitude = exifInterface.
                getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String latitude = exifInterface.
                getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        return new ImageInformation(longitude, latitude);
    }
}
