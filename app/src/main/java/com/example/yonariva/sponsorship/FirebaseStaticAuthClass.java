package com.example.yonariva.sponsorship;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseStaticAuthClass {

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
}
