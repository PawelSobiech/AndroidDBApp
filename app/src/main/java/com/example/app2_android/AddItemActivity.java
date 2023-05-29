package com.example.app2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private Button cancelButton;
    private Button webButton;
    private Button saveButton;
    private EditText manufacturerET;
    private EditText modelET;
    private EditText andVerET;
    private EditText webET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cancelButton = findViewById(R.id.fabCancelButton);
        webButton = findViewById(R.id.fabWebButton);
        saveButton = findViewById(R.id.fabSaveButton);
        manufacturerET = findViewById(R.id.fabManET);
        modelET = findViewById(R.id.fabModelET);
        andVerET = findViewById(R.id.fabAndVerET);
        webET = findViewById(R.id.fabWebET);

        cancelButton.setOnClickListener(cancelListener);
        webButton.setOnClickListener(webListener);
        saveButton.setOnClickListener(saveListener);
    }

    View.OnClickListener cancelListener = view ->
    {
        finish();
    };
    View.OnClickListener webListener = view ->
    {

    };
    View.OnClickListener saveListener = view ->
    {
        Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
        intent.putExtra("producer", manufacturerET.getText().toString());
        intent.putExtra("model", modelET.getText().toString());
        intent.putExtra("androidVersion", andVerET.getText().toString());
        intent.putExtra("website", webET.getText().toString());
        setResult(RESULT_OK, intent);
        startActivity(intent);
    };
}