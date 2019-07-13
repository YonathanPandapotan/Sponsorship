package com.example.yonariva.sponsorship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class fragmentFeed extends Fragment implements RecyclerViewAdapter.ItemListener{

    RecyclerView recyclerView;
    ArrayList<DataPostingan> arrayList;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().getRoot();

    RecyclerViewAdapter adapter;

    public fragmentFeed(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        recyclerView = (RecyclerView) getView().findViewById(R.id.RecyclerView);

        arrayList = new ArrayList();
        adapter =  new RecyclerViewAdapter(getContext(), arrayList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.child("post").getChildren().iterator();
                arrayList.clear();
                adapter.notifyDataSetChanged();

                while ( i.hasNext())
                {
                    arrayList.add(((DataSnapshot)i.next()).getValue(DataPostingan.class));
                    adapter.notifyItemInserted(arrayList.size() - 1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(DataPostingan item) {
        Intent intent = new Intent(getActivity(), ActivityPost.class);
        intent.putExtra("post", item);

        startActivity(intent);

    }

}
