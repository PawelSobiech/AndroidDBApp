package com.example.app2_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ElementListAdapter.OnItemClickListener {
    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fabMain = findViewById(R.id.fabMain);
        mAdapter = new ElementListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabMain.setOnClickListener(fabListener);
        mElementViewModel = new ViewModelProvider(this).get(ElementViewModel.class);

        mElementViewModel.getAllElements().observe(this, elements -> mAdapter.setElementList(elements));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Element element = mAdapter.getElementAtPosition(position);
                mElementViewModel.delete(element);
                Toast.makeText(MainActivity.this, "Usunięto element", Toast.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
        intent.putExtra("MID", element.getMID());
        intent.putExtra("producer", element.getMProducent());
        intent.putExtra("model", element.getMModel());
        intent.putExtra("androidVersion", element.getMWersja_Android());
        intent.putExtra("website", element.getMAdres_WWW());
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    View.OnClickListener fabListener = view -> {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
                handleAddElement(data);
            } else if (requestCode == REQUEST_CODE_EDIT) {
                handleEditElement(data);
            } else {
                Toast.makeText(this, "Niepoprawny kod żądania", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Element nie został zapisany", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleAddElement(Intent data) {
        String producer = data.getStringExtra("producer");
        String model = data.getStringExtra("model");
        String androidVersion = data.getStringExtra("androidVersion");
        String website = data.getStringExtra("website");

        Element element = new Element(producer, model, androidVersion, website);
        mElementViewModel.insert(element);
        Toast.makeText(this, "Element został dodany", Toast.LENGTH_SHORT).show();
    }
    private void handleEditElement(Intent data) {
        long id = data.getLongExtra("MID", -1L);
        if (id == -1) {
            Toast.makeText(this, "Nie można zaktualizowac elementu", Toast.LENGTH_SHORT).show();
            return;
        }
        String producer = data.getStringExtra("producer");
        String model = data.getStringExtra("model");
        String androidVersion = data.getStringExtra("androidVersion");
        String website = data.getStringExtra("website");

        Element element = new Element(producer, model, androidVersion, website);
        element.setMID(id);
        mElementViewModel.update(element);
        Toast.makeText(this, "Element został zaktualizowany", Toast.LENGTH_SHORT).show();
    }
}
