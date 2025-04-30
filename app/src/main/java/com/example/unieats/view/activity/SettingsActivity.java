package com.example.unieats.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.view.adapter.SettingsAdapter;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activity time bar colour
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_settings);

        RecyclerView recyclerView = findViewById(R.id.settings_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Find the back button by ID
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // End the SettingsActivity
            finish();
        });
        List<String> settingsOptions = Arrays.asList("About Us", "Setting 2", "Setting 3");
        SettingsAdapter adapter = new SettingsAdapter(this, settingsOptions);
        recyclerView.setAdapter(adapter);
    }
}

