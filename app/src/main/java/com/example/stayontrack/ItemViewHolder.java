package com.example.stayontrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleTextView;
    private final TextView contentTextView;
    private final TextView dateTextView;

    private ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        contentTextView = itemView.findViewById(R.id.contentTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }

    public void bind(String title, String content, String date){
        titleTextView.setText(title);
        contentTextView.setText(content);
        dateTextView.setText(date);
    }

    static ItemViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

}
