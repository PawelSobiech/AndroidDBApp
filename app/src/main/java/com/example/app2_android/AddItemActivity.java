package com.example.app2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    private static final int ACTION_ADD = 1;
    private static final int ACTION_EDIT = 2;
    private Element element;
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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("action")) {
            int action = intent.getIntExtra("action", ACTION_ADD);
            if (action == ACTION_EDIT) {
                // Tryb edycji
                element = intent.getParcelableExtra("element");
                fillFormWithData(); // Metoda do wypełnienia formularza danymi elementu
            } else {
                // Tryb dodawania
                element = new Element();
            }
        } else {
            // Tryb dodawania (domyślnie)
            element = new Element();
        }
    }

    View.OnClickListener cancelListener = view ->
    {
        finish();
    };
    View.OnClickListener webListener = view ->
    {

    };
//    View.OnClickListener saveListener = view ->
//    {
//        Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
//        intent.putExtra("producer", manufacturerET.getText().toString());
//        intent.putExtra("model", modelET.getText().toString());
//        intent.putExtra("androidVersion", andVerET.getText().toString());
//        intent.putExtra("website", webET.getText().toString());
//        setResult(RESULT_OK, intent);
//        startActivity(intent);
//    };

    private View.OnClickListener saveListener = view -> {
        element.setMProducent(manufacturerET.getText().toString());
        element.setMModel(modelET.getText().toString());
        element.setMWersja_Android(andVerET.getText().toString());
        element.setMAdres_WWW(webET.getText().toString());

        Intent resultIntent = new Intent();
        resultIntent.putExtra("element", element);

        setResult(RESULT_OK, resultIntent);
        finish();
    };
    private void fillFormWithData() {
        manufacturerET.setText(element.getMProducent());
        modelET.setText(element.getMModel());
        andVerET.setText(element.getMWersja_Android());
        webET.setText(element.getMAdres_WWW());
    }
}