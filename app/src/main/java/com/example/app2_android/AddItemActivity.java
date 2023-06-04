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
    private EditText manufacturerET;
    private EditText modelET;
    private EditText andVerET;
    private EditText webET;
    private String producer, model, version, website;
    private long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Button cancelButton = findViewById(R.id.fabCancelButton);
        Button webButton = findViewById(R.id.fabWebButton);
        Button saveButton = findViewById(R.id.fabSaveButton);
        manufacturerET = findViewById(R.id.fabManET);
        modelET = findViewById(R.id.fabModelET);
        andVerET = findViewById(R.id.fabAndVerET);
        webET = findViewById(R.id.fabWebET);

        cancelButton.setOnClickListener(cancelListener);
        webButton.setOnClickListener(webListener);
        saveButton.setOnClickListener(saveListener);

        Intent intent = getIntent();
        if(intent.hasExtra("MID")) {
            producer = intent.getStringExtra("producer");
            model = intent.getStringExtra("model");
            version = intent.getStringExtra("androidVersion");
            website = intent.getStringExtra("website");
            id = intent.getLongExtra("MID", -1L);
            fillFormWithData();
        }
    }

    View.OnClickListener cancelListener = view -> {
        setResult(RESULT_CANCELED);
        finish();
    };

    View.OnClickListener webListener = view -> {
        try {
            String url = webET.getText().toString().trim();
            if (!url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Wprowadź poprawny adres URL", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Nie znaleziono domyślnej przeglądarki", Toast.LENGTH_SHORT).show();
        }
    };

    boolean validateInput()
    {
        String producer = manufacturerET.getText().toString().trim();
        String model = modelET.getText().toString().trim();
        String androidVersion = andVerET.getText().toString().trim();
        String website = webET.getText().toString().trim();

        boolean isValid = true;

        if (producer.isEmpty()) {
            manufacturerET.setError("Wpisz producenta");
            isValid = false;
        }

        if (model.isEmpty()) {
            modelET.setError("Wpisz model");
            isValid = false;
        }

        if (androidVersion.isEmpty()) {
            andVerET.setError("Wpisz wersję Androida");
            isValid = false;
        }

        if (website.isEmpty()) {
            webET.setError("Wpisz adres www");
            isValid = false;
        }
        return isValid;
    }

    View.OnClickListener saveListener = view -> {
        String producer = manufacturerET.getText().toString().trim();
        String model = modelET.getText().toString().trim();
        String androidVersion = andVerET.getText().toString().trim();
        String website = webET.getText().toString().trim();

        boolean isValid = validateInput();

        if (isValid) {
            Intent intent = new Intent();
            intent.putExtra("producer", producer);
            intent.putExtra("model", model);
            intent.putExtra("androidVersion", androidVersion);
            intent.putExtra("website", website);
            if (id != -1) {
                intent.putExtra("MID", id);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    };
    private void fillFormWithData() {
        manufacturerET.setText(producer);
        modelET.setText(model);
        andVerET.setText(version);
        webET.setText(website);
    }
}
