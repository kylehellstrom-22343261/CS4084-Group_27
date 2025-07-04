package com.example.unieats.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unieats.R;
import com.example.unieats.view.activity.AboutUsActivity;
import com.example.unieats.view.activity.BusinessLoginActivity;
import com.example.unieats.view.activity.SettingsActivity;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private final List<String> buttonLabels;
    private final Context context;

    public SettingsAdapter(Context context, List<String> buttonLabels) {
        this.context = context;
        this.buttonLabels = buttonLabels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button button;
        public ViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.setting_button);
        }
    }

    @NonNull
    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_settings_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.ViewHolder holder, int position) {
        String label = buttonLabels.get(position);
        holder.button.setText(label);

        holder.button.setOnClickListener(v -> {
            Intent intent = null;
            switch (label) {
                case "About Us":
                    intent = new Intent(context, AboutUsActivity.class);
                    break;
                case "Business Login":
                    intent = new Intent(context, BusinessLoginActivity.class);
                    break;
            }

            if (intent != null) {
                context.startActivity(intent);
            } else {
                // Optional: Handle unknown label
                Toast.makeText(context, "Feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });

    };

    @Override
    public int getItemCount() {
        return buttonLabels.size();
    }
}
