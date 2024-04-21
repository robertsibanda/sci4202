package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

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
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);


            return insets;
        });


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(this.getApplicationContext());


        //startActivity(new Intent(this, MainActivity.class));

        if (userDatabase.userDataDAO().getAllUserData().size() > 0) {

            Toast.makeText(this, "Hello and Welcome",
                    Toast.LENGTH_SHORT).show();

            UserData userData =
                    userDatabase.userDataDAO().getAllUserData().get(0);
            findViewById(R.id.btnLogin).setOnClickListener(l -> {
                EditText etUsername = findViewById(R.id.etUsername);
                EditText etPassword = findViewById(R.id.etPassword);

                if (etUsername.getText().length() > 3 && etPassword
                        .getText().length() > 3) {
                    try {
                        String username =
                                etUsername.getText().toString();
                        String password =
                                etPassword.getText().toString();

                        if (username.equals(userData.userName) &
                                password.equals(userData.password)) {

                            startActivity(new Intent(this,
                                    MainActivity.class));
                        } else {
                            Toast.makeText(this, "wrong username/password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(this, "username & password must be " +
                                    "greater that 3 characters long",
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            startActivity(new Intent(this, SignupActivity.class));
        }

        findViewById(R.id.lbl2).setOnClickListener(l -> {
            startActivity(new Intent(this, SignupActivity.class));
        });


    }
}