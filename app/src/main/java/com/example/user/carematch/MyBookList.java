package com.example.user.carematch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MyBookList extends AppCompatActivity {
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private MyBookListAdapter MyBookListAdapter;
    private List<MyBook> MyBookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);
        MyBookList = new ArrayList<>();
        MyBookListAdapter = new MyBookListAdapter(getApplicationContext(),MyBookList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) findViewById(R.id.MyBook_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(MyBookListAdapter);

        db.collection("MyBook").orderBy("MyBook_name").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String myBook_id = doc.getDocument().getId();

                            MyBook myBook = doc.getDocument().toObject(MyBook.class).withId(myBook_id);//抓ID
                            MyBookList.add(myBook);
                            MyBookListAdapter.notifyDataSetChanged();

                        }

                    }


                }
            }
        });
    }
}

