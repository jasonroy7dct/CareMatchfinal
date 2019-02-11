package com.example.user.carematch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FavoriteFragment extends android.support.v4.app.Fragment  {

    private View b;
    private ImageButton imageButton_logo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorites,container,false);


//
//        imageButton_logo=  b.findViewById(R.id.imageButton_logo);
//        imageButton_logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openBookActivity();
//
//            }
//        });

    }




}

