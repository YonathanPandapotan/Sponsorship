package com.example.yonariva.sponsorship;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityBuatAcara extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Button tombolTanggal, btn;
    private TextView textTanggal;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private TextView namaPost, tanggal, instansi, alamat, op, jabatan, jenisBantuan, unit, hp, email, deskripsi;
    private ImageButton addImage;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private LinearLayout imageContainer;
    private ArrayList<Uri> uploaded;
    private Boolean uploadDone;
    int count = 0;
    int numberUploaded =0;
    DataPostingan post;
    LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(120, 120);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_acara);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        imageContainer = findViewById(R.id.imageContainer);
        imageLayout.setMargins(20,20,20,20);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tombolTanggal = findViewById(R.id.tombolTanggal);
        textTanggal = findViewById(R.id.textTanggal);

        tombolTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btn = findViewById(R.id.btn);
        namaPost = findViewById(R.id.namaPost);
        tanggal = findViewById(R.id.textTanggal);
        instansi = findViewById(R.id.tempatAcara);
        alamat = findViewById(R.id.alamat);
        op = findViewById(R.id.namaOP);
        jabatan = findViewById(R.id.jabatanOP);
        jenisBantuan = findViewById(R.id.jenisBantuan);
        unit = findViewById(R.id.unit);
        hp = findViewById(R.id.cp);
        email = findViewById(R.id.email);
        deskripsi = findViewById(R.id.deskripsi);
        uploaded = new ArrayList<>();
        uploadDone = new Boolean(false);

        //penyimpanan ke firebase
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyiapkan data
                final ProgressDialog progressDialog = new ProgressDialog(ActivityBuatAcara.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                String key = myRef.push().getKey();
                post = new DataPostingan(namaPost.getText().toString(), tanggal.getText().toString(), alamat.getText().toString(),
                        instansi.getText().toString(), op.getText().toString(), jabatan.getText().toString(),
                        jenisBantuan.getText().toString(), unit.getText().toString(), hp.getText().toString(), email.getText().toString(), key,
                        FirebaseUtilities.getUid(), deskripsi.getText().toString());
                post.setJumlahGambar(uploaded.size());

                Map<String, Object> postValues = post.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, postValues);
                myRef.updateChildren(childUpdates);

                if(!uploaded.isEmpty()) {

                    while (count < uploaded.size()) {
                        progressDialog.setMessage("Uploading...");
                        String fileName;
                        StorageReference ref = FirebaseStorage.getInstance().getReference().child("post/" + post.getId() + "/" + (post.getId() + count + ".jpg"));
                        ref.putFile(uploaded.get(count))
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Log.d("Upload pic", "Its Uploaded inside " + uploaded.size() + " " + count +" " + numberUploaded);
                                        if(numberUploaded == uploaded.size()){
                                            Toast.makeText(ActivityBuatAcara.this, "Acara berhasil dibuat", Toast.LENGTH_LONG);
                                            Intent intent = new Intent(ActivityBuatAcara.this, ActivityPost.class);
                                            intent.putExtra("post", post);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Upload pic", "Its not uploaded");
                                        Toast.makeText(getBaseContext(), "Upload gagal, tolong dicoba lagi...", Toast.LENGTH_LONG).show();
                                    }
                                });
                        count++;
                    }
                }
                else{
                    Log.d("Upload pic", "Disinlah mulainya");

                    Toast.makeText(ActivityBuatAcara.this, "Acara berhasil dibuat", Toast.LENGTH_LONG);
                    Intent intent = new Intent(ActivityBuatAcara.this, ActivityPost.class);
                    intent.putExtra("post", post);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });

        addImage = findViewById(R.id.addImage);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findImage();
            }
        });

    }

    public void findImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST) {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            filePath = data.getClipData().getItemAt(currentItem).getUri();
                            uploaded.add(filePath);
                            final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                            ImageView baru = new ImageView(this);
                            baru.setScaleType(ImageView.ScaleType.FIT_XY);
                            baru.setLayoutParams(imageLayout);
                            baru.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                            baru.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);
                            baru.setImageBitmap(bitmap);
                            imageContainer.addView(baru);
                            currentItem++;

                        }

                    } else if (data.getData() != null) {
                        filePath = data.getData();
                        uploaded.add(filePath);
                        final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        ImageView baru = new ImageView(this);
                        baru.setScaleType(ImageView.ScaleType.FIT_XY);
                        baru.setLayoutParams(imageLayout);
                        baru.getLayoutParams().height = (int) getResources().getDimension(R.dimen.imageview_height);
                        baru.getLayoutParams().width = (int) getResources().getDimension(R.dimen.imageview_width);

                        baru.setImageBitmap(bitmap);
                        imageContainer.addView(baru);
                    }
                }

            }
        }catch(Exception e){
            Log.e("ErrorHere", e.toString());
        }
    }

    public void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                textTanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
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
}
