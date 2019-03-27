package com.example.user.carematch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyBookList extends android.support.v4.app.Fragment {


    private View MyBookListview;
    private static final String TAG = "FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private MyBookListAdapter MyBookListAdapter;
    private List<MyBook> MyBookList;

    private FirebaseAuth auth;
    private FirebaseFirestore user;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        MyBookListview = inflater.inflate(R.layout.fragment_my_book_list, container, false);

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_book_list);

        MyBookList = new ArrayList<>();
        MyBookListAdapter = new MyBookListAdapter(getApplicationContext(), MyBookList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) MyBookListview.findViewById(R.id.myBook_list);
        mMainList.setHasFixedSize(true);
        //從原先this改成getActivity
        mMainList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainList.setAdapter(MyBookListAdapter);


        auth = FirebaseAuth.getInstance();
        user = FirebaseFirestore.getInstance();


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

        return MyBookListview;
    }
}



