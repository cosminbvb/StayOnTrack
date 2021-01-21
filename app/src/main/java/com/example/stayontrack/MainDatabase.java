package com.example.stayontrack;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static volatile MainDatabase INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;

    //created an ExecutorService with a fixed thread pool that you will use to run
    //database operations asynchronously on a background thread.
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static MainDatabase.Callback sMainDatabaseCallback = new MainDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() ->{
                ItemDao dao = INSTANCE.itemDao();
                dao.deleteAll();
                /*
                Item item = new Item("Title 1", "Content 1", "11.01.2021");
                dao.insertItem(item);
                item = new Item("Title 2", "Content 2", "11.01.2021");
                dao.insertItem(item);*/
            });
        }
    };

    static MainDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MainDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MainDatabase.class, "db").addCallback(sMainDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
