package com.example.stayontrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {


    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleEditText = findViewById(R.id.textFieldTitle);
        contentEditText = findViewById(R.id.textFieldContent);

        String currentTitle = getIntent().getStringExtra("currentTitle");
        String currentContent = getIntent().getStringExtra("currentContent");

        if(currentTitle != null && currentContent != null){
            titleEditText.setText(currentTitle);
            contentEditText.setText(currentContent);
        }

        final Button button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(titleEditText.getText())){
                    titleEditText.setError("Give it a title");
                }
                else if(TextUtils.isEmpty(contentEditText.getText())){
                    contentEditText.setError("Empty content");
                }
                else{
                    Intent replyIntent = new Intent();
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();
                    replyIntent.putExtra("TitleReply", title);
                    replyIntent.putExtra("ContentReply", content);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }

            }
        });
    }
}
