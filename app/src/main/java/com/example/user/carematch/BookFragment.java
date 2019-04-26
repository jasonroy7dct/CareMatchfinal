package com.example.user.carematch;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

//import com.example.jasonroy7dct.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BookFragment extends android.support.v4.app.Fragment {



    private View BookFragmentview;
    private static final String TAG = "FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;


    private CareListAdapter CareListAdapter;
    private List<Care> CareList;
    private FirebaseAuth auth;
    private String androidId;
    ActionBar actionBar;
    private String humanId;
    public List<Human> HumanList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        BookFragmentview = inflater.inflate(R.layout.fragment_book, container, false);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        androidId = auth.getCurrentUser().getUid();


        CareList = new ArrayList<>();
        CareListAdapter = new CareListAdapter(getApplicationContext(),CareList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) BookFragmentview.findViewById(R.id.care_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mMainList.setAdapter(CareListAdapter);


        db.collection("users/" + androidId + "/Care")
                .whereEqualTo("User_id",androidId)
                .orderBy("Care_name").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String care_id = doc.getDocument().getId();

                            Care care = doc.getDocument().toObject(Care.class).withId(care_id);//抓ID
                            CareList.add(care);
                            CareListAdapter.notifyDataSetChanged();

                        }

                    }


                }
            }
        });

        return BookFragmentview;
    }
}
