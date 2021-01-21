package com.example.stayontrack;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    private final LiveData<List<Item>> mAllItems;

    public ItemViewModel(Application application){
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {return mAllItems;}

    public void insert(Item item){
        mRepository.insert(item);
    }

}
