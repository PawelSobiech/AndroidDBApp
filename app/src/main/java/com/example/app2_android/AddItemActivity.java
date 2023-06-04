// AddItemActivity.java

package com.example.app2_android;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private static final int ACTION_ADD = 1;
    private static final int ACTION_EDIT = 2;
    private Button cancelButton;
    private Button webButton;
    private Button saveButton;
    private EditText manufacturerET;
    private EditText modelET;
    private EditText andVerET;
    private EditText webET;
    private String producer, model, version, website;
    private int action = ACTION_ADD;

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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("action")) {
            action = intent.getIntExtra("action", ACTION_ADD);
            if (action == ACTION_EDIT) {
                producer = intent.getStringExtra("producer");
                model = intent.getStringExtra("model");
                version = intent.getStringExtra("androidVersion");
                website = intent.getStringExtra("website");
                fillFormWithData();
            }
        }
    }

    View.OnClickListener cancelListener = view -> {
        setResult(RESULT_CANCELED);
        finish();
    };

    View.OnClickListener webListener = view -> {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No browser found to open the website", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener saveListener = view -> {
        Intent intent = new Intent();
        intent.putExtra("action", action);
        intent.putExtra("producer", manufacturerET.getText().toString());
        intent.putExtra("model", modelET.getText().toString());
        intent.putExtra("androidVersion", andVerET.getText().toString());
        intent.putExtra("website", webET.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    };

    private void fillFormWithData() {
        manufacturerET.setText(producer);
        modelET.setText(model);
        andVerET.setText(version);
        webET.setText(website);
    }
}
