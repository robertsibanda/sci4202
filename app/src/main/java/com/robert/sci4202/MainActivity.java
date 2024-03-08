package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDatabase userDatabase = UserDatabase.getINSTANCE(this.getApplicationContext());
        List<UserData> userData = userDatabase.userDataDAO().getAllUserData();

        if (userData.isEmpty()) {
            Toast.makeText(this, "Please login to use our service", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
        }

        showFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView =findViewById(R.id.navBottomNav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.bottom_home) {
                showFragment(new HomeFragment());
                return true;
            }
            else if (item.getItemId() == R.id.bottom_data) {
                showFragment(new CareFragment());
                return true;
            }

            else if (item.getItemId() == R.id.bottom_settings) {
                showFragment(new SettingsFragment());
                return true;
            }

            else if (item.getItemId() == R.id.bottom_calender) {
                showFragment( new CalenderFragment());
                return true;
            }
            return false;
        });
    }

    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}