package com.example.yonariva.sponsorship;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {

    private Button login, daftarBaru;
    private FirebaseAuth mAuth;

    private TextView emailText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ActivityLogin.this, ActivityMainMenu.class));
//                startActivity(new Intent( ActivityLogin.this, ActivityDummy.class));
                validateForm();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        daftarBaru = findViewById(R.id.daftar);

        daftarBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ActivityDaftarBaru.class));
            }
        });
    }

    private void validateForm() {

        String email = emailText.getText().toString();
        String pass = passwordText.getText().toString();

        if(!email.isEmpty() && !pass.isEmpty()){
            signIn(email, pass);
        }
        else{
            Toast.makeText(this, "Tolong isi kolom email dan password", Toast.LENGTH_LONG);
        }

    }

    public void signIn(String email, String password){
        Log.d("ActivityLogin", "signIn:" + email);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in...");
        progressDialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            
                            progressDialog.hide();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActivityLogin.this, ActivityMainMenu.class));

                        } else {

                            Log.w("login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(ActivityLogin.this, "Autentikasi gagal, Username dan password tidak ditemukkan atau gagal",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Log.println(1, "LOGIN", "Kesalahan internal terjadi");
                            Toast.makeText(ActivityLogin.this, "Kesalahan internal terjadi",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }
}
