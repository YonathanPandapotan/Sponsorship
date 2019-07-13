package com.example.yonariva.sponsorship;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utilities {

    public static String capitalize(String value){
        String cap = value.substring(0,1).toUpperCase();
        return cap+value.substring(1);
    }

    public static void saveToChace(Bitmap pass, String fileName){
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(
                    new File(
                            android.os.Environment.getExternalStorageDirectory().getAbsolutePath(),
                            fileName));
            pass.compress(Bitmap.CompressFormat.JPEG, 90, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap getFromChace(String fileName){
        Bitmap bit;
        File f = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        bit = BitmapFactory.decodeStream(fis);
        return bit;
    }

}
