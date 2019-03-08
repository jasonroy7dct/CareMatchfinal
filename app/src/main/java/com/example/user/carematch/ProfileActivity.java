package com.example.user.carematch;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore user;

    private TextView uid;
    private TextView name;
    private TextView email;
    private TextView membership;
    private TextView phone_number;
    private TextView address;
    private ImageView changeProfile;
    private View view;
    private CircleImageView circleImageView;
    private Context context;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final String TAG = "FireLog";

    android.support.v7.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        auth = FirebaseAuth.getInstance();
        user = FirebaseFirestore.getInstance();

        uid=findViewById(R.id.uid);

        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_phone_email);
        phone_number = findViewById(R.id.profile_phone_number);
        address = findViewById(R.id.profile_address);
        membership = findViewById(R.id.profile_membership);
        changeProfile = findViewById(R.id.change_profile_image);
        circleImageView = findViewById(R.id.circleImageView);


        getUserProfile();




    }


    public void getUserProfile() {

        Log.d(TAG, "Error");

        if (auth.getCurrentUser() != null) {
            user.collection("users").document(auth.getCurrentUser().getUid())
                    .addSnapshotListener(ProfileActivity.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, final FirebaseFirestoreException e) {
                            if(documentSnapshot.exists()){
                                final User user = documentSnapshot.toObject(User.class);
                                //取得user name
                                name.setText(String.format("%s %s", user.getName(), user.getSurname()));
                                phone_number.setText(user.getPhoneNumber());
                                address.setText(user.getAddress());
                                membership.setText(user.getMembership());
                                email.setText(auth.getCurrentUser().getEmail());
                                //get user id
                                uid.setText(auth.getCurrentUser().getUid());

                                //with(getContext())改成get()
                                //Use get() Instead of with() it will work
                                //Picasso.get().load("image_URL").into(imageView);
                                Picasso.get()
                                        .load(user.getThumb_image())
                                        .placeholder(R.drawable.avatar)
                                        .networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(circleImageView, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }

//                                                                                @Override
                                            public void onError() {

                                                //with(getContext())改成get()
                                                //Use get() Instead of with() it will work
                                                //Picasso.get().load("image_URL").into(imageView);

                                                Picasso.get()
                                                        .load(user.getThumb_image())
                                                        .placeholder(R.drawable.avatar)
                                                        .into(circleImageView);
                                            }
                                        });
                            }else {

                                //
//                                TSnackbar snackbar = TSnackbar.make(view.findViewById(R.id.profile_fragment), e.getMessage(), 5000);
//                                snackbar.setActionTextColor(Color.WHITE);
//                                View snackbarView = snackbar.getView();
//                                snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
//                                TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
//                                textView.setTextColor(Color.WHITE);
//                                snackbar.show();
                            }
                        }
                    });
        }

    }
    @Override
    //換照片
    public void onStart() {
        super.onStart();
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = ProfileActivity.this;
                if(activity != null){
                    chooseImage(activity);
                }
            }
        });
    }

    public void chooseImage(Activity activity){
        Intent gallery_intent = new Intent();
        gallery_intent.setType("image/*");
        gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
        if(gallery_intent.resolveActivity(activity.getPackageManager()) != null){
            activity.startActivityForResult(Intent.createChooser(gallery_intent,"Select Image"),1);
        }
    }

}






