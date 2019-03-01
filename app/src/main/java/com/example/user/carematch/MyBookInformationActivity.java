package com.example.user.carematch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyBookInformationActivity extends AppCompatActivity {
    private static final String TAG ="MyBook";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TextView myBookName;
    public TextView myBookCare;
    public TextView myBookDetails;
    public TextView myBookTime;
    public ImageView myBookImage;

    ActionBar actionBar;

    String carename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_details);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        Intent intent = this.getIntent();//取得傳遞過來的資料
        String myBookId = intent.getStringExtra("MyBookId");
        myBookName=(TextView)findViewById(R.id.myBook_name);
        myBookCare=(TextView)findViewById(R.id.myBook_care);
        myBookTime=(TextView) findViewById(R.id.myBook_time);
        myBookDetails=(TextView) findViewById(R.id.myBook_details);
        myBookImage=(ImageView) findViewById(R.id.myBook_image);

        Task<DocumentSnapshot> documentSnapshotTask = db.collection("MyBook").document(myBookId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        myBookName.setText(document.get("MyBook_name").toString());
                        myBookTime.setText(document.get("MyBook_time").toString());
                        myBookCare.setText(document.get("MyBook_care").toString());
                        myBookDetails.setText(document.get("MyBook_details").toString());
                        String image = document.get("MyBook_image").toString();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference picReference = storageReference.child("MyBook/"+image);

                        Glide.with(myBookImage.getContext())
                                .using(new FirebaseImageLoader())
                                .load(picReference)
                                .into(myBookImage);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
