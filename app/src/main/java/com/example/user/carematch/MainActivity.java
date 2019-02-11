package com.example.user.carematch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private int fragment_status;
    private Button test;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        test= findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapActivity();

            }
        });


    }

    public void openMapActivity(){
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_book:
                            selectedFragment = new BookFragment();
                            break;

                        case R.id.nav_favorites:
                            selectedFragment = new FavoriteFragment();
                            break;

                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };



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

}
