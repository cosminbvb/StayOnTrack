package com.example.stayontrack;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/*
The ViewModel's role is to provide data to the UI and survive configuration changes.
A ViewModel acts as a communication center between the Repository and the UI.

A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
Separating your app's UI data from your Activity and Fragment classes lets you better follow the
single responsibility principle: Your activities and fragments are responsible for drawing data
to the screen, while your ViewModel can take care of holding and processing all the data needed
for the UI.
*/
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

    public void delete(Item item){
        mRepository.delete(item);
    }

    public void edit(Item item) {mRepository.edit(item);}

}
