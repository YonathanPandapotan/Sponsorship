package com.example.yonariva.sponsorship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentProfile extends Fragment {

    private Button editProfile;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView userName;

    public fragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){

        userName = getView().findViewById(R.id.userName);

        LinearLayout postAnda = getView().findViewById(R.id.postinganAnda);
        postAnda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMainMenu.class);
                intent.putExtra("tabKey", 3);
                startActivity(intent);
            }
        });

        LinearLayout buatAcara = getView().findViewById(R.id.buatAcara);
        buatAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityBuatAcara.class);
                startActivity(intent);
            }
        });

        LinearLayout gantiPassword = getView().findViewById(R.id.gantiPassword);
        gantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityChangePassword.class));
            }
        });


        editProfile = getView().findViewById(R.id.profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ActivityDetailProfile.class));
            }
        });

        database.getReference().child("user").child(FirebaseUtilities.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(Utilities.capitalize(dataSnapshot.child("nama").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseUtilities.setProfileImage((ImageView) getView().findViewById(R.id.userPic));

    }

}
