package com.example.stayontrack;

/*A Repository class abstracts access to multiple data sources.
The Repository is not part of the Architecture Components libraries,
but is a suggested best practice for code separation and architecture.
A Repository class provides a clean API for data access to the rest of
the application.

A Repository manages queries and allows you to use multiple backends.
In the most common example, the Repository implements the logic for
deciding whether to fetch data from a network or use results cached
in a local database.*/

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    ItemRepository(Application application){
        MainDatabase db = MainDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Item>> getAllItems(){
        return mAllItems;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Item item) {
        MainDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insertItem(item);
        });
    }

}
