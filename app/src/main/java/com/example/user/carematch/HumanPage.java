package com.example.user.carematch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumanPage extends AppCompatActivity {
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private ReviewListAdapter ReviewListAdapter;
    private List<Review> ReviewList;
    private FirebaseAuth auth;
    private String androidId;
    public TextView humanName;
    public TextView humanCity;
    public TextView humanAge;
    public TextView humanEducate;
    public TextView humanScore;
    public TextView humanPrice;
    public TextView humanSex;
    public TextView humanYears;
    public TextView humanExp;
    public ImageView humanImage;
    private Button addButton;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_page);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        androidId = auth.getCurrentUser().getUid();
        ReviewList = new ArrayList<>();
        ReviewListAdapter = new ReviewListAdapter(getApplicationContext(),ReviewList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) findViewById(R.id.review_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(ReviewListAdapter);
        Intent intent = this.getIntent();//取得傳遞過來的資料
        final String humanId = intent.getStringExtra("HumanId");
        humanName=(TextView)findViewById(R.id.human_name);
        humanCity=(TextView)findViewById(R.id.human_city);
        humanEducate=(TextView)findViewById(R.id.human_educate);
        humanPrice=(TextView) findViewById(R.id.human_price);
        humanExp=(TextView) findViewById(R.id.human_exp);
        humanSex=(TextView) findViewById(R.id.human_sex);
        humanAge=(TextView) findViewById(R.id.human_age);
        humanImage=(ImageView) findViewById(R.id.human_image);
        addButton=(Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, AddHumanReview.class);
                intent.putExtra("HumanId", humanId);
                Log.d(TAG,"Id: "+humanId);
                context.startActivity(intent);


            }
        });
        db.collection("HumanReview").whereEqualTo("Human_id",humanId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String review_id = doc.getDocument().getId();

                            Review review = doc.getDocument().toObject(Review.class).withId(review_id);//抓ID
                            ReviewList.add(review);
                            ReviewListAdapter.notifyDataSetChanged();

                        }

                    }


                }
            }
        });

//




        Task<DocumentSnapshot> documentSnapshotTask = db.collection("Human").document(humanId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        humanName.setText(document.get("H_name").toString());
                        humanCity.setText(document.get("H_city").toString());
                        humanEducate.setText(document.get("H_educate").toString());
                        humanPrice.setText(document.get("H_price").toString());
                        humanExp.setText(document.get("H_exp").toString());
                        humanSex.setText(document.get("H_sex").toString());
                        humanAge.setText(document.get("H_age").toString());
                        String image = document.get("H_image").toString();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference picReference = storageReference.child("Human/"+image);

                        Glide.with(humanImage.getContext())
                                .using(new FirebaseImageLoader())
                                .load(picReference)
                                .into(humanImage);


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
