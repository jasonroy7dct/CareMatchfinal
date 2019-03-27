package com.example.user.carematch;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class HumanList extends android.support.v4.app.Fragment {

    private static final String TAG ="FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private com.example.user.carematch.HumanListAdapter HumanListAdapter;
    private List<Human> HumanList;
    //區域、性別、排序
    Spinner DivisionSp,SexSp,SortSp;
    ImageButton imgButtonShowDialog;

    private View HumanListview;


    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_human_list);
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    HumanListview = inflater.inflate(R.layout.activity_human_list, container, false);

        imgButtonShowDialog = (ImageButton) HumanListview.findViewById(R.id.imageButtonShowDialog);
        imgButtonShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder humanBuilder = new AlertDialog.Builder(HumanList.this.getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_human,null);
                humanBuilder.setTitle("篩選條件");

                DivisionSp = mView.findViewById(R.id.spn_division);
                SexSp = mView.findViewById(R.id.spn_sex) ;
                SortSp = mView.findViewById(R.id.spn_sort) ;
                String division[]  = {"請選擇地區","北部", "中部", "南部", "東部"};
                String sex[]  = {"請選擇性別","男", "女"};
                String sort[]  = {"請選擇排序", "年資由高到低", "年資由低到高", "年齡由高到低","年齡由低到高"};


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HumanList.this.getActivity(), android.R.layout.simple_spinner_item,division);
                DivisionSp.setAdapter(adapter);

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(HumanList.this.getActivity(), android.R.layout.simple_spinner_item,sex);
                SexSp.setAdapter(adapter1);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(HumanList.this.getActivity(), android.R.layout.simple_spinner_item,sort);
                SortSp.setAdapter(adapter2);

                humanBuilder.setPositiveButton("篩選", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(! DivisionSp.getSelectedItem().toString().equalsIgnoreCase("請選擇地區")||
                                ! SexSp.getSelectedItem().toString().equalsIgnoreCase("請選擇性別" )||
                                ! SortSp.getSelectedItem().toString().equalsIgnoreCase("請選擇排序" )){

                            //北部、男、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //北部、男、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //北部、男、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }

                            //北部、男、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }



                            //北部、女、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //北部、女、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //北部、女、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //北部、女、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("北部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "北部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //中部、男、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //中部、男、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //中部、男、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //中部、男、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //中部、女、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //中部、女、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //中部、女、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //中部、女、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("中部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "中部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //南部、男、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //南部、男、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //南部、男、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //南部、男、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }

                            //南部、女、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //南部、女、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //南部、女、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //南部、女、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("南部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "南部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //東部、男、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }

                            //東部、男、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //東部、男、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //東部、男、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("男")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "男")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //東部、女、年資高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //東部、女、年資低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年資由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", " 女")
                                        .orderBy("H_years", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            //東部、女、年齡低到高
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由低到高")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Log.d(TAG, "Error :" + e.getMessage());
                                        } else {
                                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                                    String human_id = doc.getDocument().getId();
                                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                    HumanList.add(human);
                                                    HumanListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            //東部、女、年齡高到低
                            if(DivisionSp.getSelectedItem().toString().equalsIgnoreCase("東部")
                                    & SexSp.getSelectedItem().toString().equalsIgnoreCase("女")
                                    & SortSp.getSelectedItem().toString().equalsIgnoreCase("年齡由高到低")){
                                HumanList.clear();
                                db.collection("Human")
                                        .whereEqualTo("H_region", "東部")
                                        .whereEqualTo("H_sex", "女")
                                        .orderBy("H_age", Query.Direction.DESCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                                if (e != null) {
                                                    Log.d(TAG, "Error :" + e.getMessage());
                                                } else {
                                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                                        if (doc.getType() == DocumentChange.Type.ADDED) {
                                                            String human_id = doc.getDocument().getId();
                                                            Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                                            HumanList.add(human);
                                                            HumanListAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                            dialog.dismiss();
                        }

                    }
                });
                humanBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                humanBuilder.setView(mView);
                AlertDialog dialog = humanBuilder.create();
                dialog.show();
            }
        });

        HumanList = new ArrayList<>();
        HumanListAdapter = new HumanListAdapter(getApplicationContext(), HumanList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) HumanListview.findViewById(R.id.human_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMainList.setAdapter(HumanListAdapter);


        //試試看
        db.collection("Human")
                .orderBy("H_age", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(TAG, "Error :" + e.getMessage());
                        } else {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String human_id = doc.getDocument().getId();
                                    Human human = doc.getDocument().toObject(Human.class).withId(human_id);//抓ID
                                    HumanList.add(human);
                                    HumanListAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });


        return HumanListview;
    }


}