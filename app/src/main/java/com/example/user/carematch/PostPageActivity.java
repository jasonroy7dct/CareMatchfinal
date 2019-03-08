package com.example.user.carematch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

public class PostPageActivity extends AppCompatActivity {
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private PostListAdapter PostListAdapter;
    private List<Post> PostList;
    public TextView postTitle;
    public TextView postDesc;
    public TextView postThumb;
    public TextView postDate;
    public ImageView postImage;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        Intent intent = this.getIntent();//取得傳遞過來的資料
        String postId = intent.getStringExtra("PostId");
        postTitle=(TextView)findViewById(R.id.post_title);
        postDesc=(TextView)findViewById(R.id.post_desc);
        postDate=(TextView)findViewById(R.id.post_date);
        postThumb=(TextView) findViewById(R.id.post_like_count);
        postImage=(ImageView) findViewById(R.id.post_image);
        Task<DocumentSnapshot> documentSnapshotTask = db.collection("Post").document(postId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        postTitle.setText(document.get("Post_title").toString());
                        postDesc.setText(document.get("Post_desc").toString());
                        postDate.setText(document.get("Post_date").toString());
                        postThumb.setText(document.get("Post_thumbs").toString());
                        String image = document.get("Post_image").toString();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                        StorageReference picReference = storageReference.child("Post/"+image);

                        Glide.with(postImage.getContext())
                                .using(new FirebaseImageLoader())
                                .load(picReference)
                                .into(postImage);


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
