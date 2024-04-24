package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

public class SignupDoctorCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_doctor_complete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etProff, etHospital;
        etProff = findViewById(R.id.etProff);
        etHospital = findViewById(R.id.etHospital);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(this.getApplicationContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);


        findViewById(R.id.btnCompeteSignup).setOnClickListener(l -> {

            new Thread(() -> {
                userData.occupation = etProff.getText().toString();
                userData.organisation = etHospital.getText().toString();

                Map<String, String> params = new HashMap<>();
                params.put("fullname", userData.fullName);
                params.put("contact", userData.contact);
                params.put("usertype", userData.userType);
                params.put("public_key", userData.publicKey);
                params.put("organisation",
                        etHospital.getText().toString());
                params.put("occupation", etProff.getText().toString());
                params.put("gender", userData.gender);

                try {
                    ServerResult result = RPCRequests.sendRequest("signup",
                            params);
                    try {
                        String userid =
                                result.getResult().get("success").toString();
                        userData.userID = userid;
                        userDatabase.userDataDAO().updateUserData(userData);
                        System.out.println("Updated userid : " + userData.userID);
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

                startActivity(new Intent(this, LoginActivity.class));
            }).start();

        });


    }
}