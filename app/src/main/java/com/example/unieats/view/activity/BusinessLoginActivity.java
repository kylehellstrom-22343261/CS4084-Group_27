package com.example.unieats.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unieats.R;
import com.example.unieats.controller.BusinessUserController;
import com.example.unieats.model.BusinessUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BusinessLoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, backButton;

    private List<BusinessUser> businessUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_login);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            BusinessUserController businessUserController = new BusinessUserController();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                AtomicBoolean correctLogin = new AtomicBoolean(false);

                businessUserController.getBusinessUsers(users -> {
                    businessUsers = users;
//                    Toast.makeText(this,businessUsers.toString(), Toast.LENGTH_SHORT).show();

                    for (BusinessUser b : businessUsers) {
                        if (Objects.equals(b.getEmail(), email)) {
                            if (Objects.equals(b.getPassword(), password)) {
                                correctLogin.set(true);
                            }

                            break;
                        }
                    }

                    if (correctLogin.get()) {
//                        Toast.makeText(this, "Login correct!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BusinessLoginActivity.this, BusinessActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Handle back button click
        backButton.setOnClickListener(v -> finish());
    }
}
