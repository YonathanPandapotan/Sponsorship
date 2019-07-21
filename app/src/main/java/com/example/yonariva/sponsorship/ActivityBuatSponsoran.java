package com.example.yonariva.sponsorship;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityBuatSponsoran extends AppCompatActivity {

    private EditText judulSponsor, namaPembuat, instansi, deskripsi;
    private Button save;
    private ImageButton addImage;

    private final int PICK_IMAGE_REQUEST = 71;

    private Uri filePath;
    private LinearLayout imageContainer;
    private ArrayList<Uri> uploaded;
    private Boolean uploadDone;
    int count = 0;
    int numberUploaded =0;
    DataSponsoran baru;

    LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(120, 120);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("sponsoran");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_sponsoran);

        judulSponsor = findViewById(R.id.judulSponsoran);
        namaPembuat = findViewById(R.id.namaPembuat);
        instansi = findViewById(R.id.instansi);
        deskripsi = findViewById(R.id.deskripsi);
        save = findViewById(R.id.btn);
        addImage = findViewById(R.id.addImage);
        imageContainer = findViewById(R.id.imageContainer);
        uploaded = new ArrayList<>();
        uploadDone = new Boolean(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

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

    public void upload(){
        final ProgressDialog progressDialog = new ProgressDialog(ActivityBuatSponsoran.this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        String key = myRef.push().getKey();
        baru = new DataSponsoran(key, judulSponsor.getText().toString(), deskripsi.getText().toString(),
                namaPembuat.getText().toString(), instansi.getText().toString(), FirebaseUtilities.getUid());
        baru.setJumlahGambar(uploaded.size());

        Map<String, Object> postValues = baru.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        myRef.updateChildren(childUpdates);

        if(!uploaded.isEmpty()) {

            while (count < uploaded.size()) {
                progressDialog.setMessage("Uploading...");
                String fileName;
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("post/" + baru.getId() + "/" + (baru.getId() + count + ".jpg"));
                ref.putFile(uploaded.get(count))
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("Upload pic", "Its Uploaded inside " + uploaded.size() + " " + count +" " + numberUploaded);
                                if(numberUploaded == uploaded.size()){
                                    Toast.makeText(ActivityBuatSponsoran.this, "Acara berhasil dibuat", Toast.LENGTH_LONG);
                                    Intent intent = new Intent(ActivityBuatSponsoran.this, ActivityPost.class);
                                    intent.putExtra("post", baru);
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

            Toast.makeText(ActivityBuatSponsoran.this, "Acara berhasil dibuat", Toast.LENGTH_LONG);
            Intent intent = new Intent(ActivityBuatSponsoran.this, ActivityPost.class);
            intent.putExtra("post", baru);
            intent.putExtra("mode", "Sponsoran");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }
}
