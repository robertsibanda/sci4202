package com.robert.sci4202;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupCompleteActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_complete);
        context = this.getApplicationContext();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        Button next = findViewById(R.id.btnNextSignup2);

        RadioGroup radioGroup = findViewById(R.id.radioUserType);

        UserDatabase userDatabase = UserDatabase.getINSTANCE(context);
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            RadioButton rb = findViewById(i);
            if (rb.getText().toString().equals("Doctor")) {
                userData.userType = "doctor";
            } else if (rb.getText().toString().equals("Patient")) {
                userData.userType = "patient";
            }
        });

        RadioGroup radioGender = findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener((radioGroup12, i) -> {
            RadioButton rb = findViewById(i);
            if (rb.getText().toString().contains("Male")) {
                userData.gender = "male";
            } else if (rb.getText().toString().equals("Female")) {
                userData.gender = "female";
            }
        });

        next.setOnClickListener(l -> {
            //TODO generate keys then send data to blockchain to create
            // an account
            new Thread(() -> {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", userData.fullName);
                params.put("contact", userData.contact);
                params.put("usertype", userData.userType);
                params.put("public_key", userData.publicKey);
                params.put("gender", userData.gender);
                String url = "http://192.168.1.240:5005";

                try {
                    ServerResult result = RPCRequests.sendRequest("signup",
                            params,
                            url);
                    try {
                        result.getResult().get("success");
                    } catch (Exception ex) {
                        try {
                            Toast.makeText(this.getApplicationContext(),
                                    result.getResult().get("error").toString(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    System.out.println(result.getResult());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                userDatabase.userDataDAO().updateUserData(userData);
                startActivity(new Intent(this, LoginActivity.class));
            }).start();
        });
    }
}