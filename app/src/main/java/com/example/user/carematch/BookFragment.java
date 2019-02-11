package com.example.user.carematch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private View a;
    private static final String TAG = "FireLog";
    String ProductsName;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mMainList;
    private MyBookListAdapter MyBookListAdapter;
    private List<MyBook> MyBookList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_book, container, false);

        a = inflater.inflate(R.layout.fragment_book, container, false);


//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_mybook);
        MyBookList = new ArrayList<>();
        MyBookListAdapter = new MyBookListAdapter(getApplicationContext(), MyBookList);
        //取得RecylerView物件，設定佈局及adapter
        mMainList = (RecyclerView) a.findViewById(R.id.MyBook_list);
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
                            MyBookList.add(myBook);
                            MyBookListAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }
        });

        return a;

    }

}
//
//        v = inflater.inflate(R.layout.fragment_book,container,false);
//
//
//
//        Spinner spinner_date= (Spinner)v.findViewById(R.id.spinner_date);
//        Spinner spinner_start_time= (Spinner)v.findViewById(R.id.spinner_start_time);
//        Spinner spinner_end_time= (Spinner)v.findViewById(R.id.spinner_end_time);
//        Spinner spinner_place= (Spinner)v.findViewById(R.id.spinner_place);


//
//            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_date));
//
//            ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_start_time));

//        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_end_time));
//
//        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_place));


//
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner_date.setAdapter(myAdapter);
//
//            myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_start_time.setAdapter(myAdapter1);

//        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_end_time.setAdapter(myAdapter2);
//
//        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_place.setAdapter(myAdapter3);


//            mDisplayDate = (TextView) v.findViewById(R.id.book_date);
//
//            mDisplayDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick (View view){
//                    Calendar cal = Calendar.getInstance();
//                    int year = cal.get(Calendar.YEAR);
//                    int month = cal.get(Calendar.MONTH);
//                    int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog dialog = new DatePickerDialog(
//                            getActivity(),
//                            android.R.style.Theme_Holo_Dialog_MinWidth,
//                            mDateSetListener,
//                            year,month,day);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();
//                }
//            });
//
//            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//
//                @Override
//                public void onDateSet(DatePicker dataPicker, int year, int month, int day) {
//                    month = month + 1;
//                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//
//
//                    String date = month + "/" + day + "/" + year;
//                    mDisplayDate.setText(date);
//
//                }
//
//            };


//            return v;
//
//

//
//
//






