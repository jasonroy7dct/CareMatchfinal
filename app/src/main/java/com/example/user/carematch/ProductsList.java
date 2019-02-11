package com.example.user.carematch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsList extends AppCompatActivity {
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private ProductsListAdapter ProductsListAdapter;
    private List<Products> ProductsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        ProductsList = new ArrayList<>();
        ProductsListAdapter = new ProductsListAdapter(getApplicationContext(),ProductsList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) findViewById(R.id.products_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(ProductsListAdapter);

        db.collection("Products").orderBy("Products_name").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String products_id = doc.getDocument().getId();

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
