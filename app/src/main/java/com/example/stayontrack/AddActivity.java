package com.example.stayontrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {


    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;
    private int itemId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleEditText = findViewById(R.id.textFieldTitle);
        contentEditText = findViewById(R.id.textFieldContent);

        String currentTitle = getIntent().getStringExtra("currentTitle");
        String currentContent = getIntent().getStringExtra("currentContent");
        itemId = getIntent().getIntExtra("itemId", -1);

        if(currentTitle != null && currentContent != null){
            titleEditText.setText(currentTitle);
            contentEditText.setText(currentContent);
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(TextUtils.isEmpty(titleEditText.getText())){
                    titleEditText.setText(currentTitle);
                }
                else if(TextUtils.isEmpty(contentEditText.getText())){
                    contentEditText.setText(currentContent);
                }
                Intent replyIntent = new Intent();
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();

                content = content.replaceAll("\\s+$", "");  //trim doar in capatul drept
                title=title.replaceAll("\\s+$", "");

                replyIntent.putExtra("TitleReply", title);
                replyIntent.putExtra("ContentReply", content);
                replyIntent.putExtra("itemId", itemId);

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }
}
