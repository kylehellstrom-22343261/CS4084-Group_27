package com.example.unieats.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.unieats.R;
import com.example.unieats.model.Menu;
import com.example.unieats.model.Restaurant;
import com.example.unieats.view.fragment.BusinessDashboardFragment;
import com.example.unieats.view.fragment.RestaurantFragment;

import org.osmdroid.config.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BusinessActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private void requestPermissionsIfNecessary(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_REQUEST_CODE);
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity time bar colour
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // This sets the storage location for osmdroid tiles
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_business);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String email = (String) getIntent().getSerializableExtra("email");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if (fragment instanceof BusinessDashboardFragment) {
//            ((BusinessDashboardFragment) fragment).filterRestaurants(email);
//        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BusinessDashboardFragment())
                .commit();

        // Find the settings button by ID
        Button settingsButton = findViewById(R.id.settingsButton);
        // Set click listener for the settings button
        settingsButton.setOnClickListener(v -> {
            // Start the SettingsActivity
            Intent intent = new Intent(BusinessActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}