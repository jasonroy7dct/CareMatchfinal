package com.example.user.carematch;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BookFragment extends android.support.v4.app.Fragment {

//    private View v;
//    private Spinner spinner_date;
//    private Spinner spinner_time;
//
//    private TextView mDisplayDate;
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//

    private View BookFragmentview;
    private static final String TAG = "FireLog";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private MyBookListAdapter MyBookListAdapter;
    private List<MyBook> MyBookListFragment;

    private View MyBookListview;
    private List<MyBook> MyBookList;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_book, container, false);

        BookFragmentview = inflater.inflate(R.layout.fragment_book, container, false);


        mMainList = (RecyclerView) BookFragmentview.findViewById(R.id.MyBook_list);


        mMainList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frahome, new MyBookDetailsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        MyBookListFragment = new ArrayList<>();
        MyBookListAdapter = new MyBookListAdapter(getApplicationContext(), MyBookListFragment);
        //取得RecylerView物件，設定佈局及adapter
        //進入MyBook_list，將其改為Fragment
        mMainList = (RecyclerView) BookFragmentview.findViewById(R.id.MyBook_list);
        mMainList.setHasFixedSize(true);
//        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMainList.setAdapter(MyBookListAdapter);

        db.collection("MyBook").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {

                    Log.d(TAG, "Error :" + e.getMessage());
                } else {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String myBook_id = doc.getDocument().getId();
                            Log.d(TAG, myBook_id);

                            MyBook myBook = doc.getDocument().toObject(MyBook.class).withId(myBook_id);//抓ID
                            MyBookListFragment.add(myBook);
                            MyBookListAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }
        });



        //跳轉
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.MyBook_list, new MyBookListFragment())
//                .addToBackStack(null)
//                .commit();




        return BookFragmentview;

    }

}
