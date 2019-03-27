package com.example.user.carematch;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.example.user.carematch.newPost.PostActivity;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.example.user.carematch.Register1Activity.PReqCode;
import static com.example.user.carematch.Register1Activity.REQUESCODE;
import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore user;
    private StorageReference storageReference;


    private TextView username;
    private TextView oldname;
    private TextView email;
    private TextView phone;
    private TextView address;

    private TextView membership;

    private ImageView changeProfile;
    private View view;
    private CircleImageView circleImageView;
    private Activity activity;
    private Context context;
    private Button  BtnUpload,setupButton,set;
    ProgressBar mProgressBar;
    Bitmap compressedImageFile;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private Uri filePath;
    ProgressBar mProgress;
    Boolean isChanged = false;
    private String user_id;
    Bitmap compressedProfileImageFile;
    boolean username_exists = false;

    public TextView FavCount;


    private final int PICK_IMAGE_REQUEST = 71;


    private static final String TAG = "FireLog";


    public ProfileFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        auth = FirebaseAuth.getInstance();
        user = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        user_id = auth.getCurrentUser().getUid();


        username = view.findViewById(R.id.profile_name);
        oldname = view.findViewById(R.id.oldname);
        email = view.findViewById(R.id.profile_phone_email);
        phone = view.findViewById(R.id.profile_phone_number);
        address = view.findViewById(R.id.profile_address);

        membership = view.findViewById(R.id.profile_membership);


        changeProfile = view.findViewById(R.id.change_profile_image);
        circleImageView = view.findViewById(R.id.circleImageView);
        BtnUpload = view.findViewById(R.id.BtnUpload);
        setupButton = view.findViewById(R.id.setupButton);
        mProgressBar = view.findViewById(R.id.post_progress);

        FavCount = (TextView) view.findViewById(R.id.FavCount);


        activity = getActivity();

        getUserProfile();




        BtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(ProfileFragment.this.getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            + ContextCompat.checkSelfPermission(ProfileFragment.this.getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(ProfileFragment.this.getActivity(), "Grant Storage Read & Write Permission", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(ProfileFragment.this.getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    } else {

                        imagePicker();

                    }

                } else {
                    imagePicker();
                }
            }
        });

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewPost();


            }


        });


        set = view.findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.this.getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });



        if (auth.getCurrentUser() != null) {
            user.collection("users/" + user_id + "/favorites").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (documentSnapshots != null) {
                        if (!documentSnapshots.isEmpty()) {
                            int count = documentSnapshots.size();
                            Counts(count);
                        } else {
                            Counts(0);
                        }
                    }
                }
            });
        }


        return view;
    }

    public void Counts(int count) {
        FavCount = view.findViewById(R.id.FavCount);

        String s = Integer.toString(count);
        if(count>=0){
            FavCount.setText(s);
        }else{
            FavCount.setText(s);
        }
    }
    public void getUserProfile() {

        if (auth.getCurrentUser() != null) {
            user.collection("users").document(auth.getCurrentUser().getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, final FirebaseFirestoreException e) {
                            if(documentSnapshot.exists()){
                                final User user = documentSnapshot.toObject(User.class);

                                username.setText(String.format("%s", user.getUsername()));
                                oldname.setText(String.format("%s", user.getOldname()));
                                phone.setText(user.getPhone());
                                address.setText(user.getAddress());
                                email.setText(user.getEmail());

                                membership.setText(user.getMembership());



                            }else {

                            }
                        }
                    });
        }

    }

    private void imagePicker() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(ProfileFragment.this.getActivity());

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SelectPicture"),PICK_IMAGE_REQUEST);
    }

    private void addNewPost() {
//        final String dec = newPostDesc.getText().toString();

        if (filePath != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            final String randomName = UUID.randomUUID().toString();
            Toast.makeText(ProfileFragment.this.getActivity(), "圖片上傳中", Toast.LENGTH_SHORT).show();

            //final
            final StorageReference file_path = storageReference.child("users_photos").child(randomName + ".jpg");
            file_path.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.getResult() != null) {
                        //原本的，getDownloadUrl不能跑，先刪掉
                        final String downloadUri = task.getResult().getStorage().getDownloadUrl().toString();
//                        final String downloadUri = task.getResult().toString();

                        if (task.isSuccessful()) {
                            File newImageFile = new File(filePath.getPath());
                            try {


                                compressedImageFile = new Compressor(ProfileFragment.this.getActivity())
                                        .setMaxHeight(100)
                                        .setMaxWidth(100)
                                        .setQuality(2)
                                        .compressToBitmap(newImageFile);

//                                //
//                                Glide.with(newPostImage.getContext())
//                                        .using(new FirebaseImageLoader())
//                                        .load(file_path)
//                                        .into(newPostImage);


//
//                                //加上去
//                                Intent intent = new Intent();
//                                intent.setType("image/*");
//                                intent.setAction(intent.ACTION_GET_CONTENT);
//                                startActivityForResult(Intent.createChooser(intent,"SelectPicture"),PICK_IMAGE_REQUEST);

                            }
                            //原本是IOException
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();



                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .setAspectRatio(1, 1)
                                    .setCropShape(CropImageView.CropShape.OVAL)
                                    .start(ProfileFragment.this.getActivity());


//                            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] thumb_data = baos.toByteArray();

                            UploadTask uploadTask = storageReference.child("users_photos/raw:")
                                    .child(randomName + ".jpg").putBytes(thumb_data);

                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //原本的，getDownloadUrl不能跑，先刪掉
                                    String downloathumbUri = taskSnapshot.getStorage().getDownloadUrl().toString();
//                                    String downloathumbUri = taskSnapshot.toString();

                                    Map<String, Object> map = new HashMap<>();
//                                    map.put("image_url", randomName+ ".jpg");
                                    map.put("image", randomName+ ".jpg");


                                    user.collection("users").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProfileFragment.this.getActivity(), "大頭照上傳成功！", Toast.LENGTH_SHORT).show();
                                                Intent mainIntent = new Intent(ProfileFragment.this.getActivity(), MainActivity.class);
                                                startActivity(mainIntent);
                                                activity.finish();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(ProfileFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                                            }
                                            mProgressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                                            StorageReference ImageRef = storageRef.child("users_photos").child(randomName + ".jpg");
                                            ImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.i("delImgPostnotAdded", randomName + ".jpg removed");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.i("delImgPostnotAdded", randomName + ".jpg not Found");

                                                }
                                            });
                                            StorageReference ThumbImageRef = storageRef.child("users_photos/raw:")
                                                    .child(randomName + ".jpg");
                                            ThumbImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Log.i("delThumbImgPostnotAdded", randomName + ".jpg removed");

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.i("delThumbImgPostnotAdded", randomName + ".jpg not Found");

                                                }
                                            });
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference ImageRef = storageRef.child("users_photos").child(randomName + ".jpg");
                                    ImageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.i("delImgPostnotAdded", randomName + ".jpg removed");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("delImgPostnotAdded", randomName + ".jpg not Found");

                                        }
                                    });
                                }
                            });
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ProfileFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(ProfileFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            Toast.makeText(ProfileFragment.this.getActivity(), "請選擇圖片與撰寫文章！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), filePath);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}





