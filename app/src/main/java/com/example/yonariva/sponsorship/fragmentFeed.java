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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class fragmentFeed extends Fragment implements RecyclerViewAdapter.ItemListener, RecyclerViewAdapterSponsor.ItemListener{

    RecyclerView recyclerView;
    ArrayList<DataPostingan> arrayList;
    ArrayList<DataSponsoran> arrayList2;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().getRoot();

    RecyclerViewAdapter adapter;
    RecyclerViewAdapterSponsor adapter2;

    Spinner spin;

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
        spin = getView().findViewById(R.id.filter);
        arrayList = new ArrayList();
        arrayList2 = new ArrayList();

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String choice = (String) adapterView.getSelectedItem();
                if(choice.equals("Acara")){

                    setAcara();

                }else{

                    setSponsoran();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void setSponsoran(){
        adapter2 =  new RecyclerViewAdapterSponsor(getContext(), arrayList2, this);
        recyclerView.setAdapter(adapter2);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator i = dataSnapshot.child("sponsoran").getChildren().iterator();
                arrayList2.clear();
                adapter2.notifyDataSetChanged();

                while ( i.hasNext())
                {
                    arrayList2.add(((DataSnapshot)i.next()).getValue(DataSponsoran.class));
                    adapter2.notifyItemInserted(arrayList.size() - 1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAcara(){
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

    @Override
    public void onItemClick(DataSponsoran item) {
        Intent intent = new Intent(getActivity(), ActivityPost.class);
        intent.putExtra("mode", "sponsoran");
        intent.putExtra("postSponsoran", item);

        startActivity(intent);

    }


}
