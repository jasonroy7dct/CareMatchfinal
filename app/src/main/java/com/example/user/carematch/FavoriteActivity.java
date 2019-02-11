package com.example.user.carematch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        //set the statue bar to transparent

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //set RecycleView with Adapter

        RecyclerView recyclerView = findViewById(R.id.favorite_list);
        List<FavoriteItem> mlist = new ArrayList<>();
        mlist.add(new FavoriteItem(R.drawable.favorite_bg4,"BG1",R.drawable.favorite_profile1,150));
        mlist.add(new FavoriteItem(R.drawable.favorite_bg4,"BG2",R.drawable.favorite_profile2,200));
        mlist.add(new FavoriteItem(R.drawable.favorite_bg4,"BG2",R.drawable.favorite_profile2,200));
        mlist.add(new FavoriteItem(R.drawable.favorite_bg4,"BG2",R.drawable.favorite_profile2,200));

        FavoriteAdapter adapter = new FavoriteAdapter(this,mlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
