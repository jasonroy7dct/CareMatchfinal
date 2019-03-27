package com.example.user.carematch;

//jason
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.user.carematch.newPost.PostActivity;


public class HomeFragment extends android.support.v4.app.Fragment {

    private View a;
    private ImageButton imageButton_booking;
    private ImageButton imageButton_medical;
    private ImageButton imageButton_trans;
    private ImageButton imageButton_post;
    private ImageButton imageButton_read;

    public Context context;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Intent bookIntent;
    String bookBody = "試試看成功沒";


    //打開預約booking
    private Button testbutton;


    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View homefragmentview = inflater.inflate(R.layout.fragment_home, container, false);

//        a = inflater.inflate(R.layout.fragment_home,container,false);

//        imageButton_discuss=  homefragment.findViewById(R.id.imageButton_discuss);
//        imageButton_medical=  homefragment.findViewById(R.id.imageButton_medical);
//        imageButton_trans = homefragment.findViewById(R.id.imageButton_trans);
//        imageButton_read = homefragment.findViewById(R.id.imageButton_read);



        //打開預約booking
        imageButton_booking=(ImageButton) homefragmentview.findViewById(R.id.imageButton_booking);
        imageButton_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new Fragment_Booking())
                        .addToBackStack(null)
                        .commit();
            }
        });
        //交通預約
        imageButton_trans=(ImageButton) homefragmentview.findViewById(R.id.imageButton_trans);
        imageButton_trans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout, new Fragment_Transportation())
                            .addToBackStack(null)
                            .commit();
                }
        });
        //醫療
        imageButton_medical=(ImageButton) homefragmentview.findViewById(R.id.imageButton_medical);
        imageButton_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new ProductsSearchFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //連接到Post
        imageButton_read=(ImageButton) homefragmentview.findViewById(R.id.imageButton_read);
        imageButton_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new PostSearchFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        //連接到Blog
        imageButton_post=(ImageButton) homefragmentview.findViewById(R.id.imageButton_post);
        imageButton_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, new BlogFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });



//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            LinearLayout linearLayout = new LinearLayout(getActivity());
//            //linearLayout.setOrientation(0);
//            TextView textView = getTextView();
//            textView.setText(getChild(groupPosition, childPosition).toString());
//            linearLayout.addView(textView);
//            return linearLayout;
//        }
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//    };



//        imageButton_read=(ImageButton) homefragmentview.findViewById(R.id.imageButton_read);
//        imageButton_read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View d) {
//
//                openProfileActivity();
//
//            }
//        });




//
//        imageButton_medical.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View d) {
//                openMedicalResourseSearchActivity();
//
//            }
//        });
//        imageButton_trans.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View d) {
//                openmyTransportationActivity();
//
//            }
//
//        });
//
//        imageButton_read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View d) {
//                openArticleActivity();
//
//            }
//
//        });

        return homefragmentview;
    }



//    public void openProfileActivity(){
//        Intent intent = new Intent(HomeFragment.this.getActivity(),PostActivity.class);
//        startActivity(intent);
//    }




//    public void openMedicalResourseSearchActivity(){
//        Intent intent = new Intent(HomeFragment.this.getActivity(),Medical_Resourse_SearchActivity.class);
//        startActivity(intent);
//    }
//
//    public void openBookingActivity(){
//        Intent intent = new Intent(HomeFragment.this.getActivity(),BookingActivity.class);
//        startActivity(intent);
//    }
//
//    public void openmyTransportationActivity(){
//        Intent intent = new Intent(HomeFragment.this.getActivity(),TransportationActivity.class);
//        startActivity(intent);
//    }
//    public void openArticleActivity(){
//        Intent intent = new Intent(HomeFragment.this.getActivity(),DiscussActivity.class);
//        startActivity(intent);
//    }



}

