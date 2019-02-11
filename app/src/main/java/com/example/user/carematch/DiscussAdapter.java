package com.example.user.carematch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.myViewHolder > {


    Context mContext;
    List<FavoriteItem> mData;

    public DiscussAdapter(Context mContext, List<FavoriteItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater .from(mContext);
        View v = inflater.inflate(R.layout.favorite_item,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.background_img.setImageResource(mData.get(position).getBackground());
        holder.profile_photo.setImageResource(mData.get(position).getProfilePhoto());
        holder.fav_title.setText(mData.get(position).getProfileName());
        holder.fav_likes_number.setText(mData.get(position).getLikesNumber()+" 收藏數");


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {


        ImageView profile_photo,background_img;
        TextView fav_title,fav_likes_number;

        public myViewHolder(View itemView) {
            super(itemView);
            profile_photo = itemView.findViewById(R.id.favorite_profile);
            background_img = itemView.findViewById(R.id.card_background);
            fav_title = itemView.findViewById(R.id.favorite_title);
            fav_likes_number = itemView.findViewById(R.id.favvorite_likes_number);



        }
    }

}
