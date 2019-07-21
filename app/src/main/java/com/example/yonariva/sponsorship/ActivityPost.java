package com.example.yonariva.sponsorship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityPost extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private DataPostingan dataPost;
    private DataSponsoran dataSponsoran;
    private Toolbar mToolbar;
    private String mode;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            String mode = (String) getIntent().getSerializableExtra("mode");

            if(mode == null){
                dataPost = (DataPostingan) getIntent().getSerializableExtra("post");
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", dataPost);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new fragmentDetailAcara());
                fragmentTransaction.commit();
            }
            else{
                this.mode = "sponsoran";
                Toast.makeText(this, mode, Toast.LENGTH_LONG).show();
                dataSponsoran = (DataSponsoran) getIntent().getSerializableExtra("sponsoran");
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", dataPost);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new FragmentDetailSponsoran());
                fragmentTransaction.commit();

            }
        }catch(Exception e){

        }

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Android Menu Group Example");

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            // Handle the non group menu items here
            case R.id.editPost:
                editPost();
                return true;
            case R.id.hapusPost:
                hapusPost();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void editPost(){
        if(mode == null){
            Intent intent = new Intent(ActivityPost.this, ActivityEditAcara.class);
            intent.putExtra("post", dataPost);

            startActivity(intent);
        }
        else{

        }

    }

    public void hapusPost(){
        if(mode == null){
            ref.child("post").child(dataPost.getId()).removeValue();
        }
        else{
            ref.child("sponsoran").child(dataSponsoran.getId()).removeValue();
        }

        finish();
    }

}
