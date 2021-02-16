package com.example.stayontrack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems; //cached copy of items
    private OnItemListener mOnItemListener;

    ItemAdapter(Context context, OnItemListener onItemListener){
        mInflater = LayoutInflater.from(context);
        mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(mItems != null){
            Item current = mItems.get(position);
            String title = current.getTitle();
            if (title.length()>40){
                title = title.substring(0,40)+"...";
            }
            String content = current.getContent();
            if (content.length()>200){
                content = content.substring(0,200)+"...";
            }
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(current.getDate());
            holder.titleTextView.setText(title);
            holder.contentTextView.setText(content);
            holder.dateTextView.setText(date);
        }
    }

    void setItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mItems != null)
            return mItems.size();
        return 0;
    }

    public Item getItemAtPosition(int position){
        return mItems.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView titleTextView;
        private final TextView contentTextView;
        private final TextView dateTextView;

        OnItemListener onItemListener;

        public ItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }
}
