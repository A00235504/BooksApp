package com.aakash.booksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.booksapp.Model.Popularbooks;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PopularBooksRecyclerViewAdapter extends FirebaseRecyclerAdapter<Popularbooks, PopularBooksRecyclerViewAdapter.popularbooksViewholder> {
    public PopularBooksRecyclerViewAdapter(
            @NonNull FirebaseRecyclerOptions<Popularbooks> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull popularbooksViewholder holder, int position, @NonNull Popularbooks model)
    {
        holder.booknameTextView.setText(model.getbookname());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.bookimageImageView);

    }


    @NonNull
    @Override
    public popularbooksViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_courses_list_item, parent, false);
        return new popularbooksViewholder(view);
    }

    class popularbooksViewholder
            extends RecyclerView.ViewHolder {
        TextView booknameTextView;
        ImageView bookimageImageView;
        public popularbooksViewholder(@NonNull View itemView)
        {
            super(itemView);
            booknameTextView = itemView.findViewById(R.id.coursetextTextView);
            bookimageImageView = itemView.findViewById(R.id.courseimageImageView);
        }
    }
}