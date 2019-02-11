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

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {
    private static final String TAG = "TEST";
    public List<Products> ProductsList;
    public Context context;
    public ProductsListAdapter(Context applicationContext, List<Products> ProductsList){

        this.ProductsList=ProductsList;
        this.context = context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productsName.setText(ProductsList.get(position).getProducts_name());
        holder.productsStorename.setText(ProductsList.get(position).getProducts_storename());
        String Image=ProductsList.get(position).getProducts_image();
        Log.d(TAG,""+ProductsList.get(position).getProducts_name());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference picReference = storageReference.child("Products/"+Image);

        Glide.with(holder.productsImage.getContext())
                .using(new FirebaseImageLoader())
                .load(picReference)
                .into(holder.productsImage);

        final String products_id =ProductsList.get(position).productsId;//抓ID ,List可以替換

        //以下為cardview按鈕監聽
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=view.getContext();

                Intent intent = new Intent();
                intent.setClass(context,ProductInformationActivity.class);
                intent.putExtra("ProductsId", products_id);
                Log.d(TAG,"Id: "+products_id);
                context.startActivity(intent);


            }
        });
    }

    
    @Override
    public int getItemCount() {
        return ProductsList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView productsName;
        public TextView productsStorename;
        public ImageView productsImage;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            productsName =(TextView)mView.findViewById(R.id.products_name);
            productsStorename=(TextView)mView.findViewById(R.id.products_storename);
            productsImage=(ImageView)mView.findViewById(R.id.products_image);
        }
    }
}
