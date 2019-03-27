package com.example.user.carematch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class MyBookListAdapter extends RecyclerView.Adapter<MyBookListAdapter.ViewHolder> {

    private static final String TAG = "TEST";
    //數據

    private transPageListener mTransPageListener;//adapter跳轉fragment

    public List<MyBook> MyBookList;
    public Context context;


    private FirebaseAuth auth;
    private FirebaseFirestore user;



    public MyBookListAdapter(android.content.Context applicationContext, List<MyBook> MyBookList){

        this.MyBookList=MyBookList;
        this.context = context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mybook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        auth = FirebaseAuth.getInstance();
        user = FirebaseFirestore.getInstance();

        user.collection("users").document(auth.getCurrentUser().getUid());



//        holder.myBookName.setText(String.format("%s %s", user.getName(), user.getSurname()));



        holder.myBookName.setText(MyBookList.get(position).getMyBook_name());
        holder.myBookTime.setText(MyBookList.get(position).getMyBook_time());
        holder.myBookCare.setText(MyBookList.get(position).getMyBook_care());
//        holder.myBookDetails.setText(MyBookList.get(position).getMyBook_details());
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
//        ChangePageListener mChangePageListener;

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

//                Log.d("Flag:", "click");
//                notifyItemChanged(position);

                Context context=view.getContext();
//
//                FragmentManager fragmentManager;
//                final FragmentTransaction fragmentTransaction;
//
//                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//
//                ((MainActivity)context).setNewBookId(myBook_id);
//
//                Log.d(TAG,"Id: "+myBook_id);
//                fragmentTransaction.replace(R.id.MyBook_list, new MyBookDetailsFragment())
//                        .addToBackStack(null)
//                        .commit();
//                ((MainActivity)context).onPageSelected(1);

                Intent intent = new Intent();
                intent.setClass(context,MyBookInformationActivity.class);
                intent.putExtra("MyBookId", myBook_id);
                Log.d(TAG,"Id: "+myBook_id);
                context.startActivity(intent);

//                notifyItemChanged(position);


//                mTransPageListener.onTransPageClick();




            }
        });
    }


    @Override
    public int getItemCount() {
        return MyBookList.size();
    }

//    public FragmentManager getFragmentManager() {
//        return fragmentManager;
//    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView myBookName;
        public TextView myBookTime;
        public TextView myBookCare;
        public ImageView myBookImage;
        public TextView myBookDetails;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            myBookName =(TextView)mView.findViewById(R.id.myBook_name);
            myBookTime=(TextView)mView.findViewById(R.id.myBook_time);
            myBookCare=(TextView)mView.findViewById(R.id.myBook_care);
//            myBookDetails=(TextView)mView.findViewById(R.id.myBook_details);
            myBookImage=(ImageView)mView.findViewById(R.id.myBook_image);
        }





    }
    public interface transPageListener {
        public void onTransPageClick();

        void onTransPageClick(String classId2);
    }//adapter跳轉fragment並攜帶需要的資料

    public void setOnTransPageClickListener (transPageListener  transPageListener) {
        this.mTransPageListener = transPageListener;
    }//adapter跳轉fragment
}


