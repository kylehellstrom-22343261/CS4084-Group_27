package com.example.unieats.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.controller.FavouritesController;
import com.example.unieats.controller.RestaurantController;
import com.example.unieats.view.adapter.FavouritesAdapter;
import com.example.unieats.view.adapter.RestaurantAdapter;
import com.example.unieats.view.fragment.MenuFragment;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Context context = getApplicationContext();

        RecyclerView recyclerView = findViewById(R.id.favourites_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FavouritesController.getFavourites(context, restaurants -> {
            FavouritesAdapter adapter = new FavouritesAdapter(restaurants);

            recyclerView.setAdapter(adapter);
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
