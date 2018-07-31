package com.example.tayyab.lostandfoundapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyab.lostandfoundapp.models.Post;
import com.example.tayyab.lostandfoundapp.models.SampleModel;
import com.example.tayyab.lostandfoundapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomfeedAdapter extends RecyclerView.Adapter<CustomfeedAdapter.SampleViewHolder>{
    ArrayList<Customfeeditem> SampleModelList;
    public CustomfeedAdapter(ArrayList<Customfeeditem> SampleModel) {
        this.SampleModelList = SampleModel;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_feed_item_layout, parent, false);
        SampleViewHolder sampleViewHolder = new SampleViewHolder(view);
        return sampleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {

        Customfeeditem model = SampleModelList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, CommentsPost.class);
                context.startActivity(intent);
            }
        });
/*        byte[] decodeimageBytes = Base64.decode(model.getUserImageUrl(), Base64.DEFAULT);
        Bitmap decodedImageUser = BitmapFactory.decodeByteArray(decodeimageBytes, 0, decodeimageBytes.length);
        byte[] decodeimageBytes1 = Base64.decode(model.getPostImageUrl(), Base64.DEFAULT);
        Bitmap decodedImagePost = BitmapFactory.decodeByteArray(decodeimageBytes1, 0, decodeimageBytes.length);*/

        // holder.txtUsername.setText("asd");
        //    holder.txtDescription.setText("asdsadsa");
    }

    @Override
    public int getItemCount() {
        return SampleModelList.size();
    }

    class SampleViewHolder extends RecyclerView.ViewHolder {

        private Integer id;
        private TextView txtUsername;
        private TextView txtDescription;
        private CheckBox chkStatusLostFound;
        private ImageView imgviewUserImageUrl;
        private ImageView imgviewPostImageUrl;

        public SampleViewHolder(View itemView) {
            super(itemView);
            /*Context context = itemView.getContext();
            Intent intent = new Intent(context,CommentsPost.class);
            context.startActivity(intent);*/

            txtUsername = itemView.findViewById(R.id.txt_ProfileName);

            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgviewPostImageUrl = itemView.findViewById(R.id.imgSampleProfileImage);
            imgviewUserImageUrl = itemView.findViewById(R.id.imgviewPostPicture);
        }
    }
}