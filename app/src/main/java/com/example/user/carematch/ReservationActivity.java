package com.example.user.carematch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReservationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    private FirebaseAuth auth;
    private FirebaseFirestore user;



    private Reservation reservation = new Reservation();
    private String TAG = "LeaveApplications_FLAG";

    private String student_id = "405401217";
    private String student_name;
    private String class_id;
    private String teacher_email;
    private ArrayList<String> class_idList;


    private Spinner spinner_leave_class;
    private Spinner spinner_leave_reason;
    private static TextView text_leave_date;
    private EditText edittext_leave_content;
    private Button btn_leave_date;
    private Button btn_upload_leave_photo;
    private Button btn_leave_cancel;
    private Button btn_leave_apply;
    private SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
    private StringBuffer date;
    private ArrayList<String> classList;
    private ImageView img_leave_photo;
    private Context context;

    private final int PICK_IMAGE_REQUEST = 71;


    private Uri filePath;


    ActionBar actionBar;


    //交通
    private Spinner spinner_trans;
    private ArrayList<String> transList;
    private ArrayList<String> trans_idList;
    private String user_name;
    private String user_id;
    private String reservation_id;
    private static TextView textView_reverasation_content;
    private static TextView text_reservation_date;
    private static TextView text_reservation_time;


    private TextView uid;
    private TextView name;



    private Button button_search;
    private Button button_cancel;
    private static TextView booking_date;
    private ImageView reservation_photo;
    private String reservation_content;


    //時間
    //開始時間
    TextView choose_time;
    TextView chooseEndTime;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String ampm;

    Calendar calendar;




    //日期
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        context = this;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        //spinner_trans
        spinner_leave_class = (Spinner) findViewById(R.id.spinner_leave_class);
        //text_reservation_date
        text_leave_date = (TextView) findViewById(R.id.text_leave_date);

        btn_leave_date = (Button) findViewById(R.id.btn_leave_date);


        //交通預約

        uid=findViewById(R.id.uid);
        name = findViewById(R.id.profile_name);



        spinner_trans = (Spinner) findViewById(R.id.spinner_trans);
        text_reservation_date = (TextView) findViewById(R.id.booking_date);
        text_reservation_time = (TextView) findViewById(R.id.choose_time);



        button_search = (Button) findViewById(R.id.Button_search);
        button_cancel = (Button) findViewById(R.id.Button_cancel);


//        ArrayAdapter<CharSequence> leave_reasonList = ArrayAdapter.createFromResource(this,
//                R.array.leave_reason,
//                android.R.layout.simple_spinner_dropdown_item);
//        spinner_leave_reason.setAdapter(dataAdapter_trans);

        trans_idList = new ArrayList<>();
        transList = new ArrayList<>();
        trans_idList.add("Null");



        getTransList();
//        transList.add(0,"請選擇您與家人需要的交通");
        transList.add("復康巴士");
        transList.add("無障礙計程車");

//        List<String> transList = new ArrayList<>();
//        transList.add(0,"請選擇您與家人需要的交通");
//        transList.add("復康巴士");
//        transList.add("無障礙計程車");


        //設定交通預約Adapter
        ArrayAdapter<String> dataAdapter_trans = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, transList);
        spinner_trans.setAdapter(dataAdapter_trans);
        //attaching data adapter to spinner
        spinner_trans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if(parent.getItemAtPosition(position).equals("請選擇您與家人需要的交通"))
//                {
//                    //不用做啥
//                }
//                else{

                //需要改
                setReservation_content(trans_idList.get(spinner_trans.getSelectedItemPosition()));
            }
                    //選擇一個item
//                    String item = parent.getItemAtPosition(position).toString();

                    //提示已經選擇的item
//                    Toast.makeText(parent.getContext(),"您已選擇： "+item, Toast.LENGTH_SHORT).show();
//                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });



        //dropdown layout style
//        dataAdapter_trans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        setUser_name();

        Log.d(TAG, "reservation.setUser_name out get:" + reservation.getUser_name());

        initDate();
        date = new StringBuffer();

        //開啟日曆
        text_reservation_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker(v);
            }

        });

        //開啟時鐘
        text_reservation_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker(v);

            }

        });

//        btn_upload_leave_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                chooseImage();
//            }
//
//        });

        Log.d(TAG, "Date:" + text_reservation_date.getText().toString());


        //預約
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //打開預約清單頁面
                openReservationConfirmedList();
                //取得預約資料
//                book();
//                Log.d(TAG, "reservation.getReservation_content onClick:" + reservation.getReservation_content());
            }
        });


        //取消
        //OK
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //OK
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                reservation_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //下訂單
    private void book() {


        if (auth.getCurrentUser() != null) {
            user.collection("users").document(auth.getCurrentUser().getUid())
                    .addSnapshotListener(ReservationActivity.this, new EventListener<DocumentSnapshot>() {

                                @Override
                                public void onEvent(DocumentSnapshot documentSnapshot, final FirebaseFirestoreException e) {
                                    if(documentSnapshot.exists()) {
                                        final User user = documentSnapshot.toObject(User.class);
                                        name.setText(String.format("%s %s", user.getName(), user.getSurname()));
                                        uid.setText(auth.getCurrentUser().getUid());



                                    }
                                }
                    });
        }

    }

    //進入預約清單頁面
    public void openReservationConfirmedList(){
        Intent intent = new Intent(ReservationActivity.this,ReservationConfirmedActivity.class);
        startActivity(intent);
    }

    //OK
    private void initDate() {



        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
        text_reservation_date.setText(date);

    }

    //選擇日期
    //
        public void datePicker (View v){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
                text_reservation_date.setText(date);
                Log.d(TAG, "Date inin:" + text_reservation_date.getText().toString());
            }


        }, year, month, day).show();

        Log.d(TAG, "Date in:" + text_reservation_date.getText().toString());
    }


    //開啟時鐘
    public void timePicker(View v){

        //選擇開始時間
        text_reservation_time = findViewById(R.id.choose_time);


        //加上上面的calender
        Calendar calendar = Calendar.getInstance();

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        //設定內容
        new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int currentHour, int currentMinute) {

                                if(currentHour>=12){
                                    ampm = "PM";
                                }else{
                                    ampm = "AM";
                                }

                String time = String.format("%02d:%02d",currentHour,currentMinute)+ ampm;
                text_reservation_time.setText(time);

                            }
                        },currentHour,currentMinute,false).show();

        Log.d(TAG, "Time in:" + text_reservation_time.getText().toString());

            }


    //OK
    //設定user
    private void setUser_name() {


        db = FirebaseFirestore.getInstance();
        db.collection("User").whereEqualTo("user_id", user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user_name = document.get("user_name").toString();
                                reservation.setUser_name(user_name);
                                Log.d(TAG, "leave.setUser_name in get:" + reservation.getUser_name());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    //OK
    //設定訂單細節
    private void setReservation_content(String reservation_id) {

        db = FirebaseFirestore.getInstance();
        db.collection("Reservation").whereEqualTo("reservation_id", reservation_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                reservation_content = document.get("reservation_content").toString();
                                reservation.setReservation_content(reservation_content);
                                Log.d(TAG, "reservation.getReservation_content in get:" + reservation.getReservation_content());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SelectPicture"), PICK_IMAGE_REQUEST);

    }




    //搜尋交通
    private void apply() {



        setUser_name();

        //交通預約 改
        setReservation_content(trans_idList.get(spinner_trans.getSelectedItemPosition()));

        //訂單細節尚未用到
//        final String reservation_content = textView_reverasation_content.getText().toString();
        final String reservation_date = text_reservation_date.getText().toString();

        //一個spinner
        final String trans = spinner_trans.getSelectedItem().toString();

        final String reservation_name = reservation.getReservation_name();
        final String trans_id = trans_idList.get(spinner_trans.getSelectedItemPosition());
        final String user_name = this.user_name;
        final String user_id = this.user_id;
        final String reservation_time = reservation.getReservation_time();
        final String reservation_photoUrl = UUID.randomUUID().toString();
        final Date reservation_uploaddate = new Date();


        db = FirebaseFirestore.getInstance();


        reservation.setReservation_uploaddate(reservation_uploaddate);
        reservation.setUser_name(user_name);
        reservation.setUser_id(user_id);
        reservation.setTrans_id(trans_id);
        reservation.setReservation_name(reservation_name);
        reservation.setReservation_date(reservation_date);
        reservation.setReservation_content(reservation_content);
        reservation.setReservation_time(reservation_time);
        reservation.setReservation_photoUrl(reservation_photoUrl);


        Log.d(TAG, "reservation_photoUrl:" + reservation_photoUrl);
        StorageReference ref = storageReference.child("Reservation_photo/" + reservation_photoUrl);
        ref.putFile(filePath);
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        progressDialogs.dismiss();
//                        Toast.makeText(LeaveApplications.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialogs.dismiss();
//                        Toast.makeText(LeaveApplications.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double prodress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                        progressDialogs.setMessage("Upload" + (int) prodress + "%");
//                    }
//                });

        Log.d(TAG, "afterReservation_name" + reservation_name);
        Log.d(TAG, "afterReservation_content:" + reservation_content);

        db.collection("Reservation").add(reservation);

        Toast.makeText(this, "已送出", Toast.LENGTH_SHORT).show();
        finish();

    }



    //OK
    //get交通預約

    private void getTransList() {
        Query queryUser_id = db.collection("User").whereEqualTo("user_id", user_id);
        queryUser_id.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if (!document.isEmpty()) {
                        trans_idList.addAll((ArrayList<String>) document.getDocuments().get(0).get("trans_id"));
                        Log.d(TAG, "trans_id: " + trans_idList);
                        for (final String trans_id : trans_idList) {
                            db.collection("Reservation")
                                    .whereEqualTo("trans_id", trans_id)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    transList.add(document.get("trans_name").toString());
                                                    Log.d(TAG, "trans_id: " + trans_id);
                                                    Log.d(TAG, "trans_name: " + transList);

                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        }

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
