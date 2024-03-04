package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            findViewById(R.id.btnLogin).setOnClickListener(l -> {
                EditText etUsername =  findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);

                if (etUsername.getText().length() > 3 && etPassword.getText().length() > 3) {
                    startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(this,
                            "Username and password must \nbe > 3 characters each",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
                }
            });
            return insets;
        });
    }
}