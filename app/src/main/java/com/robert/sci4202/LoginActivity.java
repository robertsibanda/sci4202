package com.robert.sci4202;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            findViewById(R.id.btnLogin).setOnClickListener(l -> {
                EditText etUsername =  findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);

                if (etUsername.getText().length() > 3 && etPassword.getText().length() > 3) {

                }
                else {
                    Toast.makeText(this,
                            "Username and password must \nbe > 3 characters each",
                            Toast.LENGTH_SHORT).show();
                }
            });
            return insets;
        });
    }
}