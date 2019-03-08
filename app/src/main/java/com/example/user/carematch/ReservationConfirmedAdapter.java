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

public class ReservationConfirmedAdapter extends RecyclerView.Adapter<ReservationConfirmedAdapter.ViewHolder> {

    private ReservationConfirmedAdapter.transPageListener mTransPageListener;//adapter跳轉fragment
    private static final String TAG = "TEST";
    public List<ReservationConfirmed> ReservationConfirmedList;
    public Context context;

    public ReservationConfirmedAdapter(Context context, List<ReservationConfirmed> ReservationConfirmedList){

        this.ReservationConfirmedList=ReservationConfirmedList;
        this.context = context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservation_confirmed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.reservation_name.setText(ReservationConfirmedList.get(position).getReservation_name());
        holder.user_name.setText(ReservationConfirmedList.get(position).getUser_name());
        holder.reservation_date.setText(ReservationConfirmedList.get(position).getReservation_date());
        holder.reservation_time.setText(ReservationConfirmedList.get(position).getReservation_time());
        holder.reservation_content.setText(ReservationConfirmedList.get(position).getReservation_content());

//        String Image=ReservationConfirmedList.get(position).getReservation_photoUrl();
//        final String products_id =ProductsList.get(position).productsId;//抓ID ,List可以替換

        Log.d(TAG,""+ReservationConfirmedList.get(position).getReservation_name());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//        StorageReference picReference = storageReference.child("Products/"+Image);


//        Glide.with(holder.productsImage.getContext())
//                .using(new FirebaseImageLoader())
//                .load(picReference)
//                .into(holder.productsImage);


//        //以下為cardview按鈕監聽
//        holder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Context context=view.getContext();
//                notifyItemChanged(position);
//
////                mTransPageListener.onTransPageClick();
//
//
//
//                Intent intent = new Intent();
//                intent.setClass(context,ProductInformationActivity.class);
//                intent.putExtra("ProductsId", products_id);
//                Log.d(TAG,"Id: "+products_id);
//                context.startActivity(intent);
//
////                FragmentManager fragmentManager;
////                final FragmentTransaction fragmentTransaction;
////
////                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
////                fragmentTransaction = fragmentManager.beginTransaction();
////
////                fragmentTransaction.replace(R.id.homelayoutid, new ProductsInformationFragment())
////                        .addToBackStack(null)
////                        .commit();
//
//
//            }
//        });
    }

    
    @Override
    public int getItemCount() {
        return ReservationConfirmedList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView reservation_name;
        public TextView user_name;
        public TextView reservation_date;
        public TextView reservation_time;
        public TextView reservation_content;
        public ImageView reservation_image;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            reservation_name =(TextView)mView.findViewById(R.id.reservation_name);
            user_name=(TextView)mView.findViewById(R.id.user_name);
            reservation_date =(TextView)mView.findViewById(R.id.reservation_date);
            reservation_time=(TextView)mView.findViewById(R.id.reservation_time);
            reservation_content =(TextView)mView.findViewById(R.id.reservation_content);
            reservation_image=(ImageView)mView.findViewById(R.id.reservation_image);

        }
    }


    public interface transPageListener {
        public void onTransPageClick();
    }//adapter跳轉fragment並攜帶需要的資料
    public void setOnTransPageClickListener(transPageListener transPageListener) {
        this.mTransPageListener =  transPageListener;
    }//adapter跳轉fragment
}
