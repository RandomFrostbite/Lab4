package com.nvwa.lab4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicUtils {
    static Bitmap decodePic( String pPath, int scale ) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( pPath, bmOptions );
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max( photoW, photoH ) / scale;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile( pPath, bmOptions );
    }

    static Bitmap decodePic( String pPath, int width, int height ) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile( pPath, bmOptions );
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max( photoW/width, photoH/height );

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile( pPath, bmOptions );
    }
}
