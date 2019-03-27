package com.example.user.carematch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.example.user.carematch.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;
    private FirebaseFirestore user;
    private EditText name , phoneNumber  , surname;
    private Button update;
    private CircleImageView profileImg;
    private ArcProgress arcProgress;
    private ProgressBar progressBar;


    private TextView username;
    private TextView oldname;
    private TextView email;
    private TextView phone;
    private TextView address;

    private TextView membership;
    private String user_id;

    private final int PICK_IMAGE_REQUEST = 71;

    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar_normal);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        auth = FirebaseAuth.getInstance();
        user = FirebaseFirestore.getInstance();

        user_id = auth.getCurrentUser().getUid();


        username = findViewById(R.id.profile_name);
        oldname = findViewById(R.id.profile_oldname);
        email = findViewById(R.id.profile_phone_email);
        phone = findViewById(R.id.profile_phone_number);
        address = findViewById(R.id.profile_address);
        membership = findViewById(R.id.profile_membership);

        profileImg = findViewById(R.id.userProfileImgHeader);
        arcProgress = findViewById(R.id.arc_progress);
        profileImg.setOnClickListener(this);
        update = findViewById(R.id.update_button);
        update.setOnClickListener(this);
        progressBar = findViewById(R.id.edit_profile_progress);

        getUserProfile();

    }

    public void getUserProfile() {

        if (auth.getCurrentUser() != null) {
            user.collection("users").document(auth.getCurrentUser().getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, final FirebaseFirestoreException e) {
                            if(documentSnapshot.exists()){
                                final User user = documentSnapshot.toObject(User.class);
                                username.setText(user.getUsername());
                                oldname.setText(user.getOldname());
                                phone.setText(user.getPhone());
                                address.setText(user.getAddress());


                                CropImage.activity()
                                        .setGuidelines(CropImageView.Guidelines.ON)
                                        .setAspectRatio(1, 1)
                                        .setCropShape(CropImageView.CropShape.OVAL)
                                        .start(EditProfileActivity.this);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"SelectPicture"),PICK_IMAGE_REQUEST);


                            }else {
                                showError(e.getMessage());
                            }
                        }
                    });
        }

    }

    public void chooseImage(){
        Intent gallery_intent = new Intent();
        gallery_intent.setType("image/*");
        gallery_intent.setAction(Intent.ACTION_GET_CONTENT);
        if(gallery_intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(Intent.createChooser(gallery_intent,"Select Image"),1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.uploadImage(data,requestCode,resultCode,EditProfileActivity.this,auth,arcProgress);
    }

    public void updateUser(String nm ,String old ,String ad , String phone ){
        if(TextUtils.isEmpty(nm) || TextUtils.isEmpty(old) || TextUtils.isEmpty(ad) || TextUtils.isEmpty(phone)){
            showError("請輸入完整");
        }else{
            update.setClickable(false);
            update.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            if(auth.getCurrentUser()!= null){
                Map<String,String> map = new HashMap<>();
                map.put("username",nm);
                map.put("phone",phone);
                map.put("oldname",old);
                map.put("address",ad);

                user.collection("users").document(auth.getCurrentUser().getUid())
                        .set(map, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    showSuccess("已更新 \uD83D\uDE03");
                                    update.setClickable(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    update.setVisibility(View.VISIBLE);
                                }else {
                                    if(task.getException() != null)
                                        showError(task.getException().getMessage());
                                    update.setClickable(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    update.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userProfileImgHeader : {
                chooseImage();
            }
            case R.id.update_button : {
                updateUser(username.getText().toString()
                        ,oldname.getText().toString()
                        ,address.getText().toString()
                        ,phone.getText().toString());

            }
        }

    }

    public void showError(String s) {
        TSnackbar snackbar = TSnackbar.make(findViewById(R.id.content), s, 5000);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showSuccess(String s) {
        TSnackbar snackbar = TSnackbar.make(findViewById(R.id.content), s, 5000);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.green));
        TextView textView = snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
