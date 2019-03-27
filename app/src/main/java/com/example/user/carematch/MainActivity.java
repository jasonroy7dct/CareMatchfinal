package com.example.user.carematch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{


    //設定id
    private String newBookId;
    private FragmentManager childFragmentManager;

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
    private PagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    //Tab設定頁面
    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private FavoriteFragment favoriteFragment;
    private ProfileFragment profileFragment;
    private MapsFragment mapsFragment;

    private FirebaseAuth mAuth;
    private FirebaseFirestore user;
    FirebaseFirestore firebaseFirestore;

    String current_user_id;





    FragmentManager getChildFragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        actionBar.setCustomView(R.layout.actionbar);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);




        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        homeFragment = new HomeFragment();
        bookFragment = new BookFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
        mapsFragment = new MapsFragment();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        //預設
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout, new HomeFragment())
                .commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        if (i != null) {
            String string = i.getStringExtra("fromWhere");
            if (string != null) {
                if (string.equals("fromProfile")) {
                    setfragment(profileFragment);
                    bottomNavigationView.setSelectedItemId(R.id.profile);
                } else if (string.equals("fromBook")) {
                    setfragment(bookFragment);
                    bottomNavigationView.setSelectedItemId(R.id.book);
                } else if (string.equals("fromFavorite")) {
                    setfragment(favoriteFragment);
                    bottomNavigationView.setSelectedItemId(R.id.favorite);
                }
                else if (string.equals("fromHome")) {
                    setfragment(homeFragment);
                    bottomNavigationView.setSelectedItemId(R.id.home);
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.framelayout);


        switch (item.getItemId()) {
            case R.id.favorites: {
                setfragment(favoriteFragment);
                return true;
            }
            case R.id.home: {
                setfragment(homeFragment);
                return true;
            }
            case R.id.profile: {
                setfragment(profileFragment);

                return true;
            }
            case R.id.book: {
                setfragment(bookFragment);
                return true;
            }

            default:
                setfragment(homeFragment);
                return true;
        }
    }


    public void setfragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }


//
//    public void setfragment(Fragment fragment) {
//
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }



//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Utils.uploadImage(data,requestCode,resultCode,MainActivity.this,mAuth,null);
//    }





//
////        final ActionBar actionBar = getActionBar();
//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//        actionBar.setCustomView(R.layout.actionbar);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//        mAuth = FirebaseAuth.getInstance();
//
//
//
//        viewPager = (ViewPager) findViewById(R.id.mainviewPager);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        //Tab註冊Listener
//        viewPager.addOnPageChangeListener(this);
//        tabLayout.addOnTabSelectedListener(this);
//
//        //將Fragment匯入到viewPager
//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position) {
//                    case 0:
//                        return homeFragment;
//                    case 1:
//                        return bookFragment;
//                    case 2:
//                        return favoriteFragment;
//                    case 3:
//                        return profileFragment;
//                }
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                return 4;
//            }
//        });
//
//    }
//
//
//
//
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
            case R.id.action_maps:
//                startActivity(new Intent(MainActivity.this, MapssActivity.class));

                   setfragment(mapsFragment);
                   return true;
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


    public FragmentManager getChildFragmentManager() {
        return childFragmentManager;
    }
}



//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        //TabLayout中TabItem被點擊時觸發
//        viewPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab){
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab){
//
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        //viewPager滑動後觸發
//        tabLayout.getTabAt(position).select();
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state){
//
//    }
//
//
//}






