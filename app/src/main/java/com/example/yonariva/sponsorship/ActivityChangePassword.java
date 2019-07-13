package com.example.yonariva.sponsorship;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityChangePassword extends AppCompatActivity {

    private EditText passLama, passBaru, rePassBaru;
    private TextView second;
    private Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        this.passLama = findViewById(R.id.passLama);
        this.passBaru = findViewById(R.id.passBaru);
        this.rePassBaru = findViewById(R.id.rePassBaru);
        this.selesai = findViewById(R.id.selesai);
        second = findViewById(R.id.textView26);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    public void update(){
        final String passLama = this.passLama.getText().toString();
        final String passBaru = this.passBaru.getText().toString();
        final String rePassBaru = this.rePassBaru.getText().toString();

        if(!passLama.isEmpty() && !passBaru.isEmpty() && !rePassBaru.isEmpty()){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), passLama);

            Log.d("Hulala", "hERE WE ARE");
            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(passBaru.equals(rePassBaru)) {
                                user.updatePassword(passBaru).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Hulala", "Password updated");
                                            Toast.makeText(getBaseContext(), "Password updated", Toast.LENGTH_LONG);
                                        } else {
                                            Log.d("Hulala", "Error password not updated");
                                            Toast.makeText(getBaseContext(), "Error password not updated", Toast.LENGTH_LONG);
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d("Hulala", "Error auth failed");
                            Toast.makeText(getBaseContext(), "Error auth failed", Toast.LENGTH_LONG);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Hulala", e.toString());
                }
            });
        }
        else{
            second.setText("isi kolomnya");
            Toast.makeText(this, "Tolong isi semua kolom", Toast.LENGTH_LONG);
        }
    }
}
