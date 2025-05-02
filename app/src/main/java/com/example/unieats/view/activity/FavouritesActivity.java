package com.example.unieats.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.FavouritesController;
import com.example.unieats.view.adapter.FavouritesAdapter;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private FavouritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Context context = getApplicationContext();

        RecyclerView recyclerView = findViewById(R.id.favourites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavouritesAdapter(new ArrayList<>(), restaurant -> {
            Intent intent = new Intent(FavouritesActivity.this, MainActivity.class);
            intent.putExtra("restaurant_name", restaurant.getBusinessName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        recyclerView.setAdapter(adapter);

        FavouritesController.getFavourites(context, restaurants -> adapter.updateData(restaurants));

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Context context = getApplicationContext();
        FavouritesController.getFavourites(context, restaurants -> {
            if (adapter != null) {
                adapter.updateData(restaurants);
            }
        });
    }

}
