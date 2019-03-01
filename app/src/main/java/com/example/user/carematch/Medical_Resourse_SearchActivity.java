package com.example.user.carematch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Medical_Resourse_SearchActivity extends AppCompatActivity {

    private static final String TAG ="FireLog";
    String ProductsName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private ProductsListAdapter ProductsListAdapter;
    private List<Products> ProductsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_medical_resource_search);



        ProductsList = new ArrayList<>();
        ProductsListAdapter = new ProductsListAdapter(getApplicationContext(),ProductsList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) findViewById(R.id.products_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMainList.setAdapter(ProductsListAdapter);

        db.collection("Products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                }
                else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String products_id = doc.getDocument().getId();
                            Log.d(TAG, products_id);

                            Products products = doc.getDocument().toObject(Products.class).withId(products_id);//抓ID
                            ProductsList.add(products);
                            ProductsListAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }
        });
    }
}
