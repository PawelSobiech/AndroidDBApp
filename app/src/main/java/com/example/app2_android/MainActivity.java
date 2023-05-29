package com.example.app2_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;
    private FloatingActionButton fabMain;

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

        Bundle bundleItem = getIntent().getExtras();
        if(bundleItem != null)
        {
            String producer = bundleItem.getString("producer");
            String model = bundleItem.getString("model");
            String androidVersion = bundleItem.getString("androidVersion");
            String website = bundleItem.getString("website");
            Element element = new Element(producer, model, androidVersion, website);
            mElementViewModel.insert(element);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data)
        { Toast.makeText(this,"Clearing the data...",
                Toast.LENGTH_SHORT).show();
            mElementViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener fabListener = view ->
    {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        startActivity(intent);
    };

}
