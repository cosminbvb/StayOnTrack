package com.example.stayontrack;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ItemListAdapter extends ListAdapter<Item, ItemViewHolder> {

    public ItemListAdapter(@NonNull DiffUtil.ItemCallback<Item> diffCallBack){
        super(diffCallBack);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item current = getItem(position);
        holder.bind(current.getTitle(), current.getContent(), current.getDate());
    }

    static class ItemDiff extends DiffUtil.ItemCallback<Item> {

        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            if(!oldItem.getTitle().equals(newItem.getTitle()))
                return false;
            if(!oldItem.getContent().equals(newItem.getContent()))
                return false;
            return oldItem.getContent().equals(newItem.getDate());
        }
    }
}
