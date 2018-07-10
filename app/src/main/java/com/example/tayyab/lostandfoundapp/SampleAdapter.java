/*
package com.example.tayyab.lostandfoundapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tayyab.lostandfoundapp.models.SampleModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleViewHolder> {
    List<SampleModel> SampleModelList;

    public SampleAdapter(List<SampleModel> SampleModel) {
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

        SampleModel model = SampleModelList.get(position);

        // holder.txtUsername.setText("asd");
        //    holder.txtDescription.setText("asdsadsa");

        holder.txtUsername.setText(model.getUsername());
        holder.txtDescription.setText(model.getDescription());

        Picasso.get().load(model.getPostImageUrl()).into(holder.imgviewPostImageUrl);
        Picasso.get().load(model.getPostImageUrl()).into(holder.imgviewUserImageUrl);

        // it can be set using picasso
        //holder.imgviewPostImageUrl

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

            txtUsername = itemView.findViewById(R.id.txt_ProfileName);

            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgviewPostImageUrl = itemView.findViewById(R.id.imgSampleProfileImage);
            imgviewUserImageUrl = itemView.findViewById(R.id.imgviewPostPicture);
        }
    }
}
*/
