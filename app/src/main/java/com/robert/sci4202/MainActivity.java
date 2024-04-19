package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.navBottomNav);


        UserDatabase userDatabase = UserDatabase.getINSTANCE(this.getApplicationContext());
        List<UserData> userData = userDatabase.userDataDAO().getAllUserData();


        if (userData.isEmpty()) {
            Toast.makeText(this, "Please login to use our service", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
        }
        else {
            showFragment(new HomeFragment());

            if(userData.get(0).userType.equals("doctor")) {
                try{
                    bottomNavigationView.inflateMenu(R.menu.bottom_menu_doc);
                }catch (Exception ex) {
                    System.out.println("Error at navbar : " + ex.getMessage());
                }
            }

            else {
                try{
                    bottomNavigationView.inflateMenu(R.menu.bottom_menu);
                }catch (Exception ex) {
                    System.out.println("Error at navbar : " + ex.getMessage());
                }
            }
        }


        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.bottom_home) {
                showFragment(new HomeFragment());
                return true;
            }
            else if (item.getItemId() == R.id.bottom_data) {
                if (userDatabase.userDataDAO().getAllUserData().get(0).userType.equals("doctor")) {
                   //show care fragment for doctors
                    showFragment(new DoctorPatientCareFragment());
                    return true;
                }
                else {
                    showFragment(new CareFragment());
                    return true;
                }
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