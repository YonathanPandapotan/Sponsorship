package com.example.yonariva.sponsorship;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class fragmentChatList extends Fragment {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("chat");
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList();
    private ArrayList<String> roomList = new ArrayList<>();

    private ListView chatList;

    public fragmentChatList() {
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
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        chatList= (ListView)getView().findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_rooms);
        chatList.setAdapter(arrayAdapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while ( i.hasNext())
                {
                    DataSnapshot get = ((DataSnapshot) i.next());
                    String key = get.getKey();
                    roomList.add(key);
                    String[] asd = key.split("and");
//                    set.add(asd[0]);
                    if(asd[0].equals(UserPreferences.getUserId(getActivity().getBaseContext()))){
                        set.add(get.child("info").child(asd[1]).getValue().toString());
                    }
                    else if(asd[1].equals(UserPreferences.getUserId(getActivity().getBaseContext()))){
                        set.add(get.child("info").child(asd[0]).getValue().toString());
                    }
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent I = new Intent(getActivity(), ActivityChatRoom.class);
                I.putExtra("room_name",roomList.get(i));
                startActivity(I);
            }
        });

    }

}
