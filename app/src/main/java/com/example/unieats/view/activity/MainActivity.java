package com.example.unieats.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.unieats.R;
import com.example.unieats.view.fragment.RestaurantFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity time bar colour
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new RestaurantFragment())
                .commit();

        // Find the favourites button by ID
        Button favouritesButton = findViewById(R.id.favouritesButton);
        // Set click listener for the favourites button
        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FavouritesActivity
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

        // Find the settings button by ID
        Button settingsButton = findViewById(R.id.settingsButton);
        // Set click listener for the settings button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SettingsActivity
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}