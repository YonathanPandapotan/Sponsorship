package com.example.yonariva.sponsorship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityDetailProfile extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Button editProfil;
    private ImageButton profileGambar;
    private TextView username, instansi, jabatan, alamat, email, nohp, warning;
    private TableLayout tab;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DataUser baru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        tab = findViewById(R.id.tableLayout);
        initiate();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editProfil = findViewById(R.id.editProfile);

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDetailProfile.this, ActivityEditProfile.class));
            }
        });

        profileGambar = findViewById(R.id.gambarProfile);
        profileGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityDetailProfile.this, ActivityUploadGambarProfile.class));
            }
        });

        FirebaseUtilities.setProfileImage(profileGambar);

        database.getReference().child("user").child(FirebaseUtilities.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                baru = dataSnapshot.getValue(DataUser.class);
                setDataUser();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDataUser(){
        if(baru.getRegistComplete() == false){
            tab.setVisibility(View.GONE);
            warning.setText("Tolong lengkapi data profile anda");
            username.setText(baru.getNama());
            email.setText(baru.getEmail());
        }
        else{
            warning.setVisibility(View.GONE);
            username.setText(baru.getNama());
            instansi.setText(baru.getNamaInstansi());
            jabatan.setText(baru.getJabatanUser());
            alamat.setText(baru.getAlamatInstansi());
            email.setText(baru.getEmail());
            nohp.setText(baru.getNoHP());
        }
    }

    public void initiate(){
        username = findViewById(R.id.userName);
        instansi = findViewById(R.id.instansi);
        jabatan = findViewById(R.id.jabatan);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);
        nohp = findViewById(R.id.hp);
        warning = findViewById(R.id.warning);
    }

}
