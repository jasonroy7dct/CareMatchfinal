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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{

    private static final String TAG = "TEST";
    public List<Post> PostList;
    public Context context;
    public PostListAdapter(Context context, List<Post> PostList){

        this.PostList=PostList;
        this.context =context;
    }
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_post, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.postTitle.setText(PostList.get(position).getPost_title());
        holder.postDesc.setText(PostList.get(position).getPost_desc());
        holder.postDate.setText(PostList.get(position).getPost_date());
        String Image=PostList.get(position).getPost_image();
        final String post_id =PostList.get(position).postId;//抓ID ,List可以替換


        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference picReference = storageReference.child("Post/"+Image);

        Glide.with(holder.postImage.getContext())
                .using(new FirebaseImageLoader())
                .load(picReference)
                .into(holder.postImage);


        //以下為cardview按鈕監聽
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context context=view.getContext();

                Intent intent = new Intent();
                intent.setClass(context, PostPageActivity.class);
                intent.putExtra("PostId", post_id);
                Log.d(TAG,"Id: "+post_id);
                context.startActivity(intent);


            }
        });
    }
    @Override
    public int getItemCount() {
        return PostList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView postTitle;
        public TextView postDesc;
        public ImageView postImage;
        public TextView postDate;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            postTitle =(TextView)mView.findViewById(R.id.post_title);
            postDesc =(TextView)mView.findViewById(R.id.post_desc);
            postImage=(ImageView)mView.findViewById(R.id.post_image);
            postDate = (TextView) mView.findViewById(R.id.post_date);
        }

    }
}
