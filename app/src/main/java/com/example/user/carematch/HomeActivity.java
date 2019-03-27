//package com.example.user.carematch;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.Objects;
//
//public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//
//    private HomeFragment homeFragment;
//    private BookFragment bookFragment;
//    private FavoriteFragment favoriteFragment;
//    private ProfileFragment profileFragment;
//
//    private BottomNavigationView bottomNavigationView;
//    private ImageView cart;
//
//    private FirebaseAuth mAuth;
//    private TextView toolbarTitle;
//
//    ActionBar actionBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//        actionBar.setCustomView(R.layout.actionbar);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//
//
//
//
//
//        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//
//        homeFragment = new HomeFragment();
//        bookFragment = new BookFragment();
//        favoriteFragment = new FavoriteFragment();
//        profileFragment = new ProfileFragment();
//
//        mAuth = FirebaseAuth.getInstance();
//    }
//
//
////
////    @Override
////    protected void onStart() {
////        super.onStart();
////        Intent i = getIntent();
////        if (i != null) {
////            String string = i.getStringExtra("fromWhere");
////            if (string != null) {
////                if (string.equals("fromProfile")) {
////                    setfragment(profileFragment);
////                    bottomNavigationView.setSelectedItemId(R.id.profile);
////                } else if (string.equals("fromBook")) {
////                    setfragment(bookFragment);
////                    bottomNavigationView.setSelectedItemId(R.id.book);
////                } else if (string.equals("fromFavorite")) {
////                    setfragment(favoriteFragment);
////                    bottomNavigationView.setSelectedItemId(R.id.favorite);
////                }
////            }
////        }
////    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.favorite: {
//                setfragment(favoriteFragment);
//                return true;
//            }
//            case R.id.home: {
//                setfragment(homeFragment);
//                return true;
//            }
//            case R.id.profile: {
//                setfragment(profileFragment);
//
//                return true;
//            }
//            case R.id.book: {
//                setfragment(bookFragment);
//
//                return true;
//            }
//            default:
//                return false;
//        }
//    }
//
//
//    public void setfragment(android.support.v4.app.Fragment fragment) {
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout, fragment).commit();
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Utils.uploadImage(data,requestCode,resultCode,HomeActivity.this,mAuth,null);
//    }
//}
