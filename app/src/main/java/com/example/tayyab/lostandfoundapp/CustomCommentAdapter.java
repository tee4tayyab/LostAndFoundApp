package com.example.tayyab.lostandfoundapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomCommentAdapter extends RecyclerView.Adapter<CustomCommentAdapter.SampleView> {
    ArrayList<CustomCommentitem> SampleList;

    public CustomCommentAdapter(ArrayList<CustomCommentitem> sampleList) {
        SampleList = sampleList;
    }

    @NonNull
    @Override
    public CustomCommentAdapter.SampleView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_comment_layout, parent, false);
        SampleView commentView = new SampleView(view);
        return commentView;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCommentAdapter.SampleView holder, int position) {

        CustomCommentitem model = SampleList.get(position);
        holder.txtUsername.setText(model.getUsername());
        holder.Comments.setText(model.getComments());

    }

    @Override
    public int getItemCount() {
        return SampleList.size();
    }


    class SampleView extends RecyclerView.ViewHolder{
        private Integer id;
        private TextView txtUsername;
        private TextView Comments;
        private ImageButton delete;
        private ImageButton edit;
        public SampleView(View itemView) {
            super(itemView);

            /*Context context = itemView.getContext();
            Intent intent = new Intent(context,CommentsPost.class);
            context.startActivity(intent);*/

            txtUsername = itemView.findViewById(R.id.txtUsername);

            Comments = itemView.findViewById(R.id.edtComment);
            delete = itemView.findViewById(R.id.btndelete);
            edit = itemView.findViewById(R.id.btnEdit);

        }
    }



}
