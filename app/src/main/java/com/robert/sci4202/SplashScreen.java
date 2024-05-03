package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(this.getApplicationContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen duration
                if (userData.ingoreLogin) {
                    Intent intent = new Intent(SplashScreen.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this,
                            LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }
}