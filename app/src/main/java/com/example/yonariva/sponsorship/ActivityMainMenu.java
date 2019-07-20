package com.example.yonariva.sponsorship;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityMainMenu extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // Write a message to the database
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    // Create a reference to 'images/mountains.jpg'
    StorageReference mountainImagesRef = storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

            if(menuItem.getGroupId() == R.id.menuPertama){
                switch(menuItem.getItemId()){
                    case R.id.profile:
                        break;
                    case R.id.pesan:
                        break;
                    case R.id.pemberitahuan:
                        break;
                }
            }
            else if(menuItem.getGroupId() == R.id.menuKedua){
                switch(menuItem.getItemId()) {
                    case R.id.pengaturanAkun:
                        break;
                    case R.id.gantiPass:
                        break;
                    case R.id.logout:
                            FirebaseUtilities.signOut();
                            startActivity(new Intent(ActivityMainMenu.this, ActivityLogin.class));
                        break;
                }
            }
            // set item as selected to persist highlight
            menuItem.setChecked(true);
            // close drawer when item is tapped
            drawerLayout.closeDrawers();

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            return true;
                }
            });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this ,drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        final LinearLayout asd = toolbar.findViewById(R.id.toolbarLayout);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.nav_homepage:
                    asd.setVisibility(LinearLayout.VISIBLE);
                    loadFragment(new fragmentMainMenu());
                    break;
                case R.id.nav_chat:
                    asd.setVisibility(LinearLayout.GONE);
                    loadFragment(new fragmentChatList());
                    break;
                case R.id.nav_profile:

                    asd.setVisibility(LinearLayout.GONE);
                    loadFragment(new fragmentProfile());

                    break;
                }

            return false;
            }
        });

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new fragmentMainMenu());
        fragmentTransaction.commit();

        View headerView = navigationView.getHeaderView(0);
        final TextView username = (TextView) headerView.findViewById(R.id.namaPengguna);

        database.getReference().child("user").child(FirebaseUtilities.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] split = dataSnapshot.child("nama").getValue().toString().trim().split("\\s+");
//                username.setText(Utilities.capitalize(split[0]));
                UserPreferences.setUserName(getBaseContext(), Utilities.capitalize(split[0]));
                UserPreferences.setUserId(getBaseContext(),FirebaseUtilities.getUid());
                username.setText(UserPreferences.getUserName(getBaseContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUtilities.setProfileImage((ImageView) navigationView.getHeaderView(0).findViewById(R.id.userProfile));
    }

    public void loadFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes

    }

    public Fragment getCurrentFragment()
    {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        return currentFragment;
    }

    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 2) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
