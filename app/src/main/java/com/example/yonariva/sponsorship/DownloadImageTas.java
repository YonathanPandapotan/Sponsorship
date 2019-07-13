package com.example.yonariva.sponsorship;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;

class DownloadImageTas extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    String fileName;

    public DownloadImageTas(ImageView bmImage, String fileName) {
        this.bmImage = bmImage;
        this.fileName = fileName;
    }


    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        Utilities.saveToChace(result, fileName);
    }

}