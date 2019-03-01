//package com.example.user.carematch;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.example.user.carematch.Model.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreSettings;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import static android.text.TextUtils.isEmpty;
//import static com.example.user.carematch.Check.doStringsMatch;
//
//
//public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
//    private static final String TAG = "RegisterActivity";
//
//    private ImageView ImgUserPhoto;
//    private static int PReqCode = 1;
//    private static int REQUESCODE = 1;
//    private Uri pickedImgUri;
//
//    //widgets
//    private EditText mName,mEmail, mPassword, mConfirmPassword;
//    private ProgressBar mProgressBar;
//
//    //vars
//    private FirebaseFirestore mDb;
//    FirebaseAuth mAuth;
//    DatabaseReference reference;
//
//
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register1);
//
//
//        mName = (EditText) findViewById(R.id.editName);
//        mEmail = (EditText) findViewById(R.id.editEmail);
//        mPassword = (EditText) findViewById(R.id.editPassword);
//        mConfirmPassword = (EditText) findViewById(R.id.editConfirmedPassword);
//        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        findViewById(R.id.btn_register).setOnClickListener(this);
//
//        mDb = FirebaseFirestore.getInstance();
//
//        hideSoftKeyboard();
//
//
//        ImgUserPhoto = findViewById(R.id.logo);
//        ImgUserPhoto.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//
//                if (Build.VERSION.SDK_INT>=22){
//
//                    checkAndRequestForPermission();
//                }else{
//                    openGallery();
//                }
//            }
//
//
//        });
//    }
//
//    /**
//     * Register a new email and password to Firebase Authentication
//     * @param email
//     * @param password
//     */
//
//    public void registerNewEmail(final String email, String password){
//
//        showDialog();
//
//        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                            //insert some default data
//                            User user = new User();
//                            user.setEmail(email);
//                            user.setUsername(email.substring(0, email.indexOf("@")));
//                            user.setUser_id(FirebaseAuth.getInstance().getUid());
////                            現在要設找到大頭照，但目前卡關，User那裡已經有設，但目前在找如何取得圖片的方法。
////                            user.setProfile();
//                            updateUserInfo(email,pickedImgUri,mAuth.getCurrentUser());
//
//                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                                    .setTimestampsInSnapshotsEnabled(true)
//                                    .build();
//                            mDb.setFirestoreSettings(settings);
//
//                            DocumentReference newUserRef = mDb
//                                    .collection(getString(R.string.collection_users))
//                                    .document(FirebaseAuth.getInstance().getUid());
//
//                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    hideDialog();
//
//                                    if (task.isSuccessful()) {
//                                        redirectLoginScreen();
//                                    } else {
//                                        View parentLayout = findViewById(android.R.id.content);
//                                        Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//
//                        } else {
//                            View parentLayout = findViewById(android.R.id.content);
//                            Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
//                            hideDialog();
//                        }
//
//                        // ...
//                    }
//                });
//    }
//
//    /**
//     * Redirects the user to the login screen
//     */
//    private void redirectLoginScreen(){
//        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");
//
//        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//
//    private void showDialog(){
//        mProgressBar.setVisibility(View.VISIBLE);
//
//    }
//
//    private void hideDialog(){
//        if(mProgressBar.getVisibility() == View.VISIBLE){
//            mProgressBar.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void hideSoftKeyboard(){
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_register:{
//                Log.d(TAG, "onClick: attempting to register.");
//
//                //check for null valued EditText fields
//                if(!isEmpty(mEmail.getText().toString())
//                        && !isEmpty(mPassword.getText().toString())
//                        && !isEmpty(mConfirmPassword.getText().toString())){
//
//                    //check if passwords match
//                    if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){
//
//                        //Initiate registration task
//                        registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
//                    }else{
//                        Toast.makeText(RegisterActivity.this, "密碼不相符！", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//                    Toast.makeText(RegisterActivity.this, "請填寫正確！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            }
//        }
//    }
//
//
//
//    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
//
//        //上傳照片至firebase storage and get url
//        StorageReference mstorage = FirebaseStorage.getInstance().getReference().child("users_photos");
//        final StorageReference imageFilePath = mstorage.child(pickedImgUri.getLastPathSegment());
//        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                //相片已上傳
//                //取得相片url
//                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(name)
//                                .setPhotoUri(uri)
//                                .build();
//
//                        currentUser.updateProfile(profileUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            //user.info上傳成功
//                                            showMessage("註冊成功");
//                                            updateUI();
//
//                                        }
//
//
//                                    }
//                                });
//
//                    }
//                });
//
//
//
//            }
//        });
//
//
//    }
//
//    private void updateUI() {
//        Intent MainActivity = new Intent(getApplicationContext(), com.example.user.carematch.MainActivity.class);
//        startActivity(MainActivity);
//        finish();
//    }
//
//    private void showMessage(String message) {
//        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
//
//
//    }
//
//
//    private void openGallery(){
//        //打開相簿更換大頭照
//
//        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent,REQUESCODE);
//    }
//
//
//
//
//    private void checkAndRequestForPermission() {
//
//        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
//                Toast.makeText(RegisterActivity.this, "請接受認可許可",Toast.LENGTH_SHORT).show();
//            }
//            else{
//                ActivityCompat.requestPermissions
//                        (RegisterActivity.this,
//                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
//                                PReqCode);
//            }
//        }
//        else
//            openGallery();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null){
//
//            //使用者已經成功挑選照片
//
//            pickedImgUri = data.getData();
//            ImgUserPhoto.setImageURI(pickedImgUri);
//
//
//        }
//    }
//}