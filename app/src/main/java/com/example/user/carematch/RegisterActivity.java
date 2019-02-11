package com.example.user.carematch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {
    ProgressBar progressBar;
    private EditText editName, editEmail, editPassword;
    private Button btn_register;
    private TextView textLogin;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btn_register = (Button) findViewById(R.id.btn_register);
        textLogin = (TextView) findViewById(R.id.textLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        btn_register.setOnClickListener(this);
        textLogin.setOnClickListener(this);

    }

    private void registerUser() {
        final String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        final String name = editName.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("請輸入信箱");
            editEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("請輸入有效的信箱");
            editEmail.requestFocus();
            return;
        } else if (password.isEmpty()) {
            editPassword.setError("請輸入密碼");
            editPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            editPassword.setError("密碼不得低於6位數");
            editPassword.requestFocus();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String userid = firebaseUser.getUid();

                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("Id", userid);
                        hashMap.put("Name", name);
                        hashMap.put("Email",email);
//                        hashMap.put("imageURL", "default");
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        //   startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "已註冊", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

        }
    }
    @Override
    public void onClick (View view){
        if (view == btn_register) {
            registerUser();
        }
        if (view == textLogin) {
            //開啟login activity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        }
    }

}
