package com.example.app2_android;

import static android.content.Intent.ACTION_EDIT;

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
    private static final int EDIT_ELEMENT_REQUEST_CODE = 1;

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
    @Override
    public void onItemClickListener(Element element) {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra("action", 2);
        intent.putExtra("element", element);
        startActivity(intent);
    }
    View.OnClickListener fabListener = view ->
    {
        Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
        intent.putExtra("action", 1);
        startActivity(intent);
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ELEMENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int action = data.getIntExtra("action", 2);
            if (action == 2) {
                Element editedElement = data.getParcelableExtra("element");
                mElementViewModel.update(editedElement);
            }
        }
    }

}
