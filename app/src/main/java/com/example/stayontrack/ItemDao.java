package com.example.stayontrack;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getAllItems();

    @Insert
    void insertItem(Item item);

    @Insert
    public void insertAllItems(List<Item> itemList);

    @Query("DELETE FROM Item")
    public void deleteAll();

}
