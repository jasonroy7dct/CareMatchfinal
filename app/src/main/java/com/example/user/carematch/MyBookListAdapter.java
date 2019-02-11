package com.example.user.carematch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class MyBookListAdapter extends RecyclerView.Adapter<MyBookListAdapter.ViewHolder> {

    private static final String TAG = "TEST";
    public List<MyBook> MyBookList;
    public Context context;
    public MyBookListAdapter(Context applicationContext, List<MyBook> MyBookList){

        this.MyBookList=MyBookList;
        this.context = context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mybook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myBookName.setText(MyBookList.get(position).getMyBook_name());
        holder.myBookTime.setText(MyBookList.get(position).getMyBook_time());
        holder.myBookCare.setText(MyBookList.get(position).getMyBook_care());
        String Image=MyBookList.get(position).getMyBook_image();
        Log.d(TAG,""+MyBookList.get(position).getMyBook_name());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference picReference = storageReference.child("MyBook/"+Image);

        Glide.with(holder.myBookImage.getContext())
                .using(new FirebaseImageLoader())
                .load(picReference)
                .into(holder.myBookImage);

        final String myBook_id =MyBookList.get(position).myBookId;//抓ID ,List可以替換

        //以下為cardview按鈕監聽
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=view.getContext();

                Intent intent = new Intent();
                intent.setClass(context,MyBookInformationActivity.class);
                intent.putExtra("ProductsId", myBook_id);
                Log.d(TAG,"Id: "+myBook_id);
                context.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount() {
        return MyBookList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView myBookName;
        public TextView myBookTime;
        public TextView myBookCare;
        public ImageView myBookImage;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            myBookName =(TextView)mView.findViewById(R.id.myBook_name);
            myBookTime=(TextView)mView.findViewById(R.id.myBook_time);
            myBookCare=(TextView)mView.findViewById(R.id.myBook_care);
            myBookImage=(ImageView)mView.findViewById(R.id.myBook_image);
        }
    }
}


