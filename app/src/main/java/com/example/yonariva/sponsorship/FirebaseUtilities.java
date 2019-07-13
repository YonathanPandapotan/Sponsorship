package com.example.yonariva.sponsorship;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class FirebaseUtilities {

    public static String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    }

    public static String getEmail(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
    }

    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public static void checkPass(){
        FirebaseAuth.getInstance().getCurrentUser().updatePassword("asdasd");
    }

    public static void setProfileImage(final ImageView pass){
        Bitmap bit = Utilities.getFromChace("UserFotoProfile");
        if(bit == null){
            FirebaseStorage.getInstance().getReference().child(FirebaseUtilities.getUid()).child("profileImages.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                new DownloadImageTas((ImageView) pass, "UserFotoProfile").execute(uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }
        else{
            pass.setImageBitmap(bit);
        }

    }

    public static void getDownloadURL(DataPostingan dataPost, int count, final ArrayList<String> urlDownload){

        FirebaseStorage.getInstance().getReference().child("post").child(dataPost.getId()).child(dataPost.getId() + "0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                urlDownload.add(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Here it is cunt", "Failed");
            }
        });

    }

    public static void downloadImage(final String name, final ImageView pass){
        FirebaseStorage.getInstance().getReference().child("post").child(name).child(name+"0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                new DownloadImageTas((ImageView) pass, name).execute(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

}
