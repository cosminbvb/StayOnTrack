package com.example.stayontrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemListener {

    private ItemAdapter adapter;
    private ItemViewModel mItemViewModel;

    public static final int NEW_ITEM_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_ITEM_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(this, this);
        recyclerView.setAdapter(adapter);

        TextView empty = findViewById(R.id.empty_prompt); // textView for the "no notes" message

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> itemList) {
                // itemList is the new data
                if(itemList.size() != 0){
                    empty.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
                adapter.setItems(itemList);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, NEW_ITEM_ACTIVITY_REQUEST_CODE);
            }
        });

        // handler for right swipe action => delete item
        // TODO - left swipe => feature (eg adding to a folder)
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Item myItem = adapter.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                mItemViewModel.delete(myItem);
            }
        });

        helper.attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if the request was adding a new item, then we add it to the db
        if(requestCode == NEW_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String title = data.getStringExtra("TitleReply");
            String content = data.getStringExtra("ContentReply");
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            // we don t need an id since its auto generated
            Item item = new Item(title, content, date);
            if(!title.isEmpty() || !content.isEmpty())
                mItemViewModel.insert(item);
        }
        // if the request was editing an existing item, then we update the item in the db
        // because the Item object has an auto generated primary key, inserting a new item takes care
        // of the primary key. But when updating an item, the whole object is needed, meaning that
        // we also need to attach its id before calling the edit method.
        if(requestCode == EDIT_ITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String title = data.getStringExtra("TitleReply");
            String content = data.getStringExtra("ContentReply");
            String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
            int id = data.getIntExtra("itemId", -1);
            Item item = new Item(title, content, date);
            item.setId(id);
            mItemViewModel.edit(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        // because we want to update an item, we need to attach its id
        Item toEdit = adapter.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("currentTitle", toEdit.getTitle());
        intent.putExtra("currentContent", toEdit.getContent());
        intent.putExtra("itemId", toEdit.getId());
        startActivityForResult(intent, EDIT_ITEM_ACTIVITY_REQUEST_CODE);

    }
}