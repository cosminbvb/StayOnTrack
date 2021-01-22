package com.example.stayontrack;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static volatile MainDatabase INSTANCE;

    static MainDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MainDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MainDatabase.class, "db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sMainDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sMainDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


}
