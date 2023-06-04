package com.example.app2_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.OnItemClickListener {
    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private FloatingActionButton fabMain;

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    private static final int ACTION_EDIT = 2;
    private static final int ACTION_ADD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        fabMain = findViewById(R.id.fabMain);
        mAdapter = new ElementListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabMain.setOnClickListener(fabListener);
        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        mElementViewModel.getAllElements().observe(this, elements -> {
            mAdapter.setElementList(elements);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mElementViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(Element element) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra("action", ACTION_EDIT);
        intent.putExtra("producer", element.getMProducent());
        intent.putExtra("model", element.getMModel());
        intent.putExtra("androidVersion", element.getMWersja_Android());
        intent.putExtra("website", element.getMAdres_WWW());
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    View.OnClickListener fabListener = view -> {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra("action", ACTION_ADD);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            int action = data.getIntExtra("action", ACTION_ADD);

            if (action == ACTION_ADD) {
                String producer = data.getStringExtra("producer");
                String model = data.getStringExtra("model");
                String androidVersion = data.getStringExtra("androidVersion");
                String website = data.getStringExtra("website");

                Element element = new Element(producer, model, androidVersion, website);
                mElementViewModel.insert(element);
                Toast.makeText(this, "Element added", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            int action = data.getIntExtra("action", ACTION_EDIT);

            if (action == ACTION_EDIT) {
                String producer = data.getStringExtra("producer");
                String model = data.getStringExtra("model");
                String androidVersion = data.getStringExtra("androidVersion");
                String website = data.getStringExtra("website");

                Element element = new Element(producer, model, androidVersion, website);
                mElementViewModel.update(element);
                Toast.makeText(this, "Element updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
