package com.example.yonariva.sponsorship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class ActivityEditProfile extends AppCompatActivity {

    private EditText nama, namaInstansi, alamatInstansi, jabatanUser, noHp;
    private Button selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nama = findViewById(R.id.nama);
        namaInstansi = findViewById(R.id.namaInstansi);
        alamatInstansi = findViewById(R.id.alamatInstansi);
        jabatanUser = findViewById(R.id.jabatanUser);
        noHp = findViewById(R.id.noHP);
        selesai = findViewById(R.id.selesai);

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    public void update(){
        DataUser edit = new DataUser();

        edit.setNama(nama.getText().toString());
        edit.setId(FirebaseStaticAuthClass.getUid());
        edit.setNamaInstansi(namaInstansi.getText().toString());
        edit.setAlamatInstansi(alamatInstansi.getText().toString());
        edit.setJabatanUser(jabatanUser.getText().toString());
        edit.setNoHP(noHp.getText().toString());
        edit.setEmail(FirebaseStaticAuthClass.getEmail());

        FirebaseDatabase.getInstance().getReference().child("user").child(edit.getId()).setValue(edit);

    }
}
