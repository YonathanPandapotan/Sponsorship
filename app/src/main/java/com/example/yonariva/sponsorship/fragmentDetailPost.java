package com.example.yonariva.sponsorship;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.CarouselViewPager;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class fragmentDetailPost extends Fragment {

    private DataPostingan dataPost;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("chat");
    private CarouselView car;
    private ArrayList<String> urlDownload;
    int count=0, getCount=0;
    int[] sampleImages = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //your code goes here

        Intent i = getActivity().getIntent();

        dataPost = (DataPostingan) i.getSerializableExtra("post");
        urlDownload = new ArrayList<>();

        TextView nama, tanggal, instansi, alamat, pengelola, jabatan, jenisBantuan, unit, hp, email;

        nama = getView().findViewById(R.id.namaPost);
        tanggal = getView().findViewById(R.id.tanggal);
        instansi = getView().findViewById(R.id.instansi);
        alamat = getView().findViewById(R.id.alamat);
        pengelola = getView().findViewById(R.id.pengelola);
        jabatan = getView().findViewById(R.id.jabatan);
        jenisBantuan = getView().findViewById(R.id.jenisBantuan);
        unit = getView().findViewById(R.id.unit);
        hp = getView().findViewById(R.id.hp);
        email = getView().findViewById(R.id.email);

        nama.setText(dataPost.getNamaPost());
        tanggal.setText(dataPost.getTanggal());
        instansi.setText(dataPost.getInstansi());
        alamat.setText(dataPost.getAlamat());
        pengelola.setText(dataPost.getOp());
        jabatan.setText(dataPost.getJabatan());
        jenisBantuan.setText(dataPost.getJenisBantuan());
        unit.setText(dataPost.getUnit());
        hp.setText(dataPost.getHp());
        email.setText(dataPost.getEmail());
        car = getView().findViewById(R.id.carousel);

        Button button = getView().findViewById(R.id.chat);

        while(count <= dataPost.getJumlahGambar()) {
            FirebaseStorage.getInstance().getReference().child("post").child(dataPost.getId()).child(dataPost.getId() + count + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    urlDownload.add(uri.toString());
                    getCount++;
                    if(getCount == dataPost.getJumlahGambar()){
                        car.setImageListener(imageListener);
                        car.setPageCount(getCount);
                        car.setSlideInterval(0);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("Here it is cunt", "Failed");
                }
            });
            count++;
        }

        car.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
                CarouselViewPager viewPager = car.getContainerViewPager();
                ImageView currentImage = (ImageView) viewPager.getChildAt(position);
                currentImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                currentImage.setAdjustViewBounds(true);
            }
        });

        if(dataPost.getIdUser().equals(FirebaseUtilities.getUid())){
            button.setText("Edit Postingan Ini");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        else{
            instansi.setText("False");
            button.setText("Chat pembuat acara");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put(dataPost.getIdUser()+"and"+FirebaseUtilities.getUid(),"");
                    root.updateChildren(map);
                    Intent I = new Intent(getActivity(), ActivityChatRoom.class);
                    I.putExtra("room_name",dataPost.getIdUser()+"and"+FirebaseUtilities.getUid());
                    startActivity(I);
                }
            });
        }

    }

    public void buildIt(){
        car.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                new DownloadImageTas((ImageView) imageView, dataPost.getId()+(position-1)).execute(urlDownload.get(position-1).toString());
            }
        });

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            new DownloadImageTas((ImageView) imageView, "postPic").execute(urlDownload.get(position));
            //imageView.setImageResource(sampleImages[position]);
        }
    };

}
