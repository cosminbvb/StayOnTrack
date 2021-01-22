package com.example.stayontrack;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Item")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;
    private String date;

    public Item(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String  getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
