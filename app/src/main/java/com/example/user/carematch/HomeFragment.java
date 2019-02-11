package com.example.user.carematch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomeFragment extends android.support.v4.app.Fragment {

    private View a;
    private ImageButton imageButton_book;
    private ImageButton imageButton_medical;
    private ImageButton imageButton_trans;
    private ImageButton imageButton_discuss;
    private ImageButton imageButton_read;




    void testPush() {
        Log.d("test","test使用push上傳程式");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home,container,false);

        a = inflater.inflate(R.layout.fragment_home,container,false);


        imageButton_discuss=  a.findViewById(R.id.imageButton_discuss);
        imageButton_book=  a.findViewById(R.id.imageButton_book);
        imageButton_medical=  a.findViewById(R.id.imageButton_medical);
        imageButton_trans = a.findViewById(R.id.imageButton_trans);
        imageButton_read = a.findViewById(R.id.imageButton_read);



        imageButton_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                openDiscussActivity();

            }
        });
        imageButton_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                openBookingActivity();

            }
        });
        imageButton_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                openMedicalResourseSearchActivity();

            }
        });
        imageButton_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                openmyTransportationActivity();

            }

        });

        imageButton_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View d) {
                openArticleActivity();

            }

        });

        return a;
    }



    public void openDiscussActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),DiscussActivity.class);
        startActivity(intent);
    }
    public void openMedicalResourseSearchActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),Medical_Resourse_SearchActivity.class);
        startActivity(intent);
    }

    public void openBookingActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),BookingActivity.class);
        startActivity(intent);
    }
    public void openmybookActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),mybookActivity.class);
        startActivity(intent);
    }
    public void openmyTransportationActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),TransportationActivity.class);
        startActivity(intent);
    }
    public void openArticleActivity(){
        Intent intent = new Intent(HomeFragment.this.getActivity(),DiscussActivity.class);
        startActivity(intent);
    }



}

