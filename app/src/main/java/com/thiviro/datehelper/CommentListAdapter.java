package com.thiviro.datehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends ArrayAdapter<Comment> {
    private Context context;
    int resource;

    public CommentListAdapter(@NonNull Context context, int resource, @NonNull List<Comment> comments) {
        super(context, resource, comments);
        context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Account author = null;
        /**
         * BACKEND
         *
         * Download the account of the author
         * To then get the name of the author
         */
        String name = author.getName();
        String comment = getItem(position).getComment();
        String imageURL = getItem(position).getImageURl();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        CircleImageView imageCircle = convertView.findViewById(R.id.circle_image_comment);
        TextView authorNameView = convertView.findViewById(R.id.author_name_textView);
        TextView commentView = convertView.findViewById(R.id.comment_textView);

        Glide.with(context).load(imageURL).into(imageCircle);
        authorNameView.setText(name);
        commentView.setText(comment);

        return convertView;
    }
}
