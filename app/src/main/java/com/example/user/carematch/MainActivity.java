package com.example.user.carematch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener ,
        TabLayout.OnTabSelectedListener{





    //設定id
    private String newBookId;
    public String getNewBookId() {
        return newBookId;
    }
    public void setNewBookId(String bookId) {
        this.newBookId = bookId;
    }


    private BottomNavigationView bottomNavigationView;
    private int fragment_status;
    private Button test;


    ActionBar actionBar;

    //Tab功能設計
    private ViewPager viewPager;
    private TabLayout tabLayout;
    //Tab設定頁面
    private HomeFragment homeFragment = new HomeFragment();
    private BookFragment bookFragment = new BookFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final ActionBar actionBar = getActionBar();
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.mainviewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Tab註冊Listener
        viewPager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);

        //將Fragment匯入到viewPager
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return homeFragment;
                    case 1:
                        return bookFragment;
                    case 2:
                        return favoriteFragment;
                    case 3:
                        return profileFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

    }


    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
//                startActivity(new Intent(MainActivity.this, SetUpActivity.class));
                return true;
            case R.id.action_chnage_pass:
//                startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                return true;
            case R.id.action_sign_out:
                signOut();
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //TabLayout中TabItem被點擊時觸發
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab){

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab){

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

    }

    @Override
    public void onPageSelected(int position) {
        //viewPager滑動後觸發
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state){

    }

}




//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListner);
//
//        test = findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//
//        });
//
//
//    }
//
//    public void openMapActivity() {
//        Intent intent = new Intent(this, TestFragment.class);
//        startActivity(intent);
//    }
//
//    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            selectedFragment = new HomeFragment();
//                            break;
//
//                        case R.id.nav_book:
//                            selectedFragment = new BookFragment();
//                            break;
//
//                        case R.id.nav_favorites:
//                            selectedFragment = new FavoriteFragment();
//                            break;
//
//                        case R.id.nav_profile:
//                            selectedFragment = new ProfileFragment();
//                            break;
//                    }
//
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container,
//                                    selectedFragment)
//                            .commit();
//
//                    return true;
//                }
//            };
//}
//

/*
    if (fragment_status == 1) {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    } else if (fragment_status == 2) {
        bottomNavigationView.setSelectedItemId(R.id.nav_book);
    } else if (fragment_status == 3) {
        bottomNavigationView.setSelectedItemId(R.id.nav_favorites);
    } else if (fragment_status == 4) {
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
    } else {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

    }*/



