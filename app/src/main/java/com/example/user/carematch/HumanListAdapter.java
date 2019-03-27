package com.example.user.carematch;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumanListAdapter extends RecyclerView.Adapter<HumanListAdapter.ViewHolder>{
    private static final String TAG = "TEST";
    public List<Human> HumanList;
    public Context context;

    private FirebaseAuth firebaseAuth;
    private String androidId;
    private FirebaseFirestore db;
    Button like_button;

    public HumanListAdapter(Context context, List<Human> HumanList){

        this.HumanList=HumanList;
        this.context =context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_human, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.hName.setText(HumanList.get(position).getH_name());
        holder.hExp.setText(HumanList.get(position).getH_exp());
        holder.hCity.setText(HumanList.get(position).getH_city());
        holder.hAge.setText(HumanList.get(position).getH_age());
        holder.hPrice.setText(HumanList.get(position).getH_price());
        String Image=HumanList.get(position).getH_image();

        final String currentUserID = firebaseAuth.getCurrentUser().getUid();
        final String human_id =HumanList.get(position).humanId;//抓ID ,List可以替換




        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference picReference = storageReference.child("Human/"+Image);

        Glide.with(holder.hImage.getContext())
                .using(new FirebaseImageLoader())
                .load(picReference)
                .into(holder.hImage);


        //以下為cardview按鈕監聽
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, HumanPage.class);
                intent.putExtra("HumanId", human_id);
                Log.d(TAG,"Id: "+human_id);
                context.startActivity(intent);


            }
        });


        //收藏
        if (firebaseAuth.getCurrentUser() != null) {
            db.collection("users/" + currentUserID + "/favorites").document(human_id)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                            if (documentSnapshot.exists() && e == null) {

                                holder.fav_button.setImageResource(R.drawable.cm_logo);
                            } else {
                                holder.fav_button.setImageResource(R.drawable.empty_heart);
                            }
                        }
                    });
        }
        if (firebaseAuth.getCurrentUser() != null) {
            holder.fav_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("users/" + currentUserID + "/favorites").document(human_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().exists()) {
                                    Map<String,String> favorite = new HashMap<>();
                                    favorite.put("human_id",human_id);
                                    db.collection("users/" + currentUserID + "/favorites").document(human_id).set(favorite);

                                } else {
                                    db.collection("users/" + currentUserID + "/favorites").document(human_id).delete();


                                }
                            } else {
                                Toast.makeText(context, "ERROR" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return HumanList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView hName;
        public TextView hExp;
        public ImageView hImage;
        public TextView hCity;
        public TextView hAge;
        public TextView hPrice;
        public ImageView fav_button;




        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            hName =(TextView)mView.findViewById(R.id.h_name);
            hExp =(TextView)mView.findViewById(R.id.h_exp);
            hImage=(ImageView)mView.findViewById(R.id.h_image);
            hAge = (TextView) mView.findViewById(R.id.h_age);
            hPrice = (TextView) mView.findViewById(R.id.h_price);
            hCity = (TextView) mView.findViewById(R.id.h_city);
            fav_button = (ImageView) mView.findViewById(R.id.fav_button);

        }

    }
}
