package com.example.stayontrack;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM Item ORDER BY date DESC")
    LiveData<List<Item>> getAllItems();

    @Insert
    void insertItem(Item item);

    @Insert
    void insertAllItems(List<Item> itemList);

    @Update
    void editItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Query("DELETE FROM Item")
    void deleteAll();

}
