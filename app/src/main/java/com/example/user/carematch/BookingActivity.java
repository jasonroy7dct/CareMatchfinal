package com.example.user.carematch;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity {


    private Spinner spinner_date;
    private Spinner spinner_time;


    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);




//        Spinner spinner_date= (Spinner) findViewById(R.id.spinner_date);
        Spinner spinner_start_time= (Spinner) findViewById(R.id.spinner_start_time);
        Spinner spinner_end_time= (Spinner) findViewById(R.id.spinner_end_time);
        Spinner spinner_place= (Spinner) findViewById(R.id.spinner_place);
        Spinner spinner_category= (Spinner) findViewById(R.id.spinner_category);


        List <String> items_start_time = new ArrayList<>();
        items_start_time.add(0,"請選擇您與家人要預約的時間");
        items_start_time.add("00:00");
        items_start_time.add("01:00");
        items_start_time.add("02:00");
        items_start_time.add("03:00");
        items_start_time.add("04:00");
        items_start_time.add("05:00");
        items_start_time.add("06:00");
        items_start_time.add("07:00");
        items_start_time.add("08:00");
        items_start_time.add("09:00");
        items_start_time.add("10:00");
        items_start_time.add("11:00");
        items_start_time.add("12:00");
        items_start_time.add("13:00");
        items_start_time.add("14:00");
        items_start_time.add("15:00");
        items_start_time.add("16:00");
        items_start_time.add("17:00");
        items_start_time.add("18:00");
        items_start_time.add("19:00");
        items_start_time.add("20:00");
        items_start_time.add("21:00");
        items_start_time.add("22:00");
        items_start_time.add("23:00");
        items_start_time.add("24:00");



        //style
        ArrayAdapter<String> dataAdapter_start_time;
        dataAdapter_start_time = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_start_time);

        //dropdown layout style
        dataAdapter_start_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spinner_start_time.setAdapter(dataAdapter_start_time);

        spinner_start_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("請選擇您與家人要預約的時間"))
                {
                    //不用做啥
                }
                else{
                    //選擇一個item

                    String item = parent.getItemAtPosition(position).toString();

                    //提示已經選擇的item
                    Toast.makeText(parent.getContext(),"您已選擇： "+item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        List <String> items_end_time = new ArrayList<>();
        items_end_time.add(0,"請選擇您與家人要預約的時間");
        items_end_time.add("00:00");
        items_end_time.add("01:00");
        items_end_time.add("02:00");
        items_end_time.add("03:00");
        items_end_time.add("04:00");
        items_end_time.add("05:00");
        items_end_time.add("06:00");
        items_end_time.add("07:00");
        items_end_time.add("08:00");
        items_end_time.add("09:00");
        items_end_time.add("10:00");
        items_end_time.add("11:00");
        items_end_time.add("12:00");
        items_end_time.add("13:00");
        items_end_time.add("14:00");
        items_end_time.add("15:00");
        items_end_time.add("16:00");
        items_end_time.add("17:00");
        items_end_time.add("18:00");
        items_end_time.add("19:00");
        items_end_time.add("20:00");
        items_end_time.add("21:00");
        items_end_time.add("22:00");
        items_end_time.add("23:00");
        items_end_time.add("24:00");



        //style
        ArrayAdapter<String> dataAdapter_end_time;
        dataAdapter_end_time = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_end_time);

        //dropdown layout style
        dataAdapter_end_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spinner_end_time.setAdapter(dataAdapter_end_time);

        spinner_end_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("請選擇您與家人要預約的時間"))
                {
                    //不用做啥
                }
                else{
                    //選擇一個item

                    String item = parent.getItemAtPosition(position).toString();

                    //提示已經選擇的item
                    Toast.makeText(parent.getContext(),"您已選擇： "+item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        List <String> items_place = new ArrayList<>();
        items_place.add(0,"請選擇您與家人的所在地");
        items_place.add("基隆市");
        items_place.add("新北市");
        items_place.add("台北市");
        items_place.add("桃園市");
        items_place.add("新竹縣");
        items_place.add("新竹市");
        items_place.add("苗栗縣");
        items_place.add("台中市");
        items_place.add("彰化縣");
        items_place.add("雲林縣");
        items_place.add("嘉義縣");
        items_place.add("嘉義市");
        items_place.add("台南市");
        items_place.add("高雄市");
        items_place.add("屏東縣");
        items_place.add("宜蘭縣");
        items_place.add("南投縣");
        items_place.add("花蓮縣");
        items_place.add("台東縣");




        //style
        ArrayAdapter<String> dataAdapter_place;
        dataAdapter_place = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_place);

        //dropdown layout style
        dataAdapter_place.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spinner_place.setAdapter(dataAdapter_place);

        spinner_place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("請選擇您與家人的所在地"))
                {
                    //不用做啥
                }
                else{
                    //選擇一個item

                    String item = parent.getItemAtPosition(position).toString();

                    //提示已經選擇的item
                    Toast.makeText(parent.getContext(),"您已選擇： "+item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        List <String> items_category = new ArrayList<>();
        items_category.add(0,"請選擇您與家人要預約的服務");
        items_category.add("看護");
        items_category.add("復健師");
        items_category.add("護理師");
        items_category.add("職能治療師");





        //style
        ArrayAdapter<String> dataAdapter_category;
        dataAdapter_category = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items_category);

        //dropdown layout style
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        spinner_category.setAdapter(dataAdapter_category);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("請選擇您與家人的所在地"))
                {
                    //不用做啥
                }
                else{
                    //選擇一個item

                    String item = parent.getItemAtPosition(position).toString();

                    //提示已經選擇的item
                    Toast.makeText(parent.getContext(),"您已選擇： "+item, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });



        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_date));

//        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(BookingActivity.this,
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_start_time));
//
//        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(BookingActivity.this,
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_end_time));
//
//        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(BookingActivity.this,
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.name_place));


//
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner_date.setAdapter(myAdapter);

//        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_start_time.setAdapter(myAdapter1);

//        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_end_time.setAdapter(myAdapter2);
//
//        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_place.setAdapter(myAdapter3);
//


        mDisplayDate = (TextView) findViewById(R.id.booking_date);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BookingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker dataPicker, int year, int month, int day) {
                month = month + 1;



                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);

            }

        };









    }




}

