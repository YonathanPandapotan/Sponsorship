package com.example.yonariva.sponsorship;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityDaftarBaru extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    private EditText userName, email, password, repassword;
    private TextView aadc;
    private Button regist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_baru);

        auth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.namaUser);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.rePassword);
        regist = findViewById(R.id.regist);
        aadc = findViewById(R.id.textView22);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid();
            }
        });

    }

    public void regist(){

        try{
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        DataUser baru = new DataUser();

                        baru.setNama(userName.getText().toString());
                        baru.setEmail(email.getText().toString());
                        baru.setRegistComplete(false);

                        baru.setId(user.getUid());

                        Map<String, Object> postValues = baru.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(baru.getId(), postValues);
                        myRef.updateChildren(childUpdates);
                    }
                    else{
                        Toast.makeText(ActivityDaftarBaru.this, "Terjadi kesalahan internal", Toast.LENGTH_LONG);
                    }
                    }
                });
        }
        catch (Exception e){
            Toast.makeText(ActivityDaftarBaru.this, "Kesalahan terjadi " + e.toString(), Toast.LENGTH_LONG);
        }

    }

    public void valid(){

        String a = password.getText().toString();
        String b = repassword.getText().toString();

        if(TextUtils.isEmpty(userName.getText())){
            userName.setError("Tolong isi bagian ini");
        }
        else if(TextUtils.isEmpty(email.getText())){
            email.setError("Tolong isi bagian ini");
        }
        else if(a.length() < 6){
            password.setError("Password tidak boleh lebih pendek dari 6");
        }
        else if(a == b){
            repassword.setError("Password tidak sama");
        }
        else{
            regist();
        }

    }
}
