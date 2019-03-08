package com.example.user.carematch;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReservationConfirmedList extends AppCompatActivity {

    private View ReservationConfirmedListview;
    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private ReservationConfirmedAdapter ReservationConfirmedListAdapter;
    private List<ReservationConfirmed> ReservationConfirmedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reservation_confirmed);




        ReservationConfirmedList = new ArrayList<>();
        ReservationConfirmedListAdapter = new ReservationConfirmedAdapter(getApplicationContext(),ReservationConfirmedList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) findViewById(R.id.reservation_confirmed_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(ReservationConfirmedListAdapter);

        db.collection("ReservationConfirmed").orderBy("ReservationConfirmedList_title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String reservationconfirmedId = doc.getDocument().getId();

                            ReservationConfirmed reservationConfirmed = doc.getDocument().toObject(ReservationConfirmed.class).withId(reservationconfirmedId);//抓ID
                            ReservationConfirmedList.add(reservationConfirmed);
                            ReservationConfirmedListAdapter.notifyDataSetChanged();

                        }

                    }


                }

            }
        });

    }
}
