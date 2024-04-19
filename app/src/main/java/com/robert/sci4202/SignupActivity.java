package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etUsername, etPassword, etFullName, etContact;
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etContact  = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
        UserDatabase userDatabase = UserDatabase.getINSTANCE(this.getApplicationContext());
        userDatabase.userDataDAO().deleteAll();
        JSONObject signupParams = new JSONObject();
        try {
            signupParams.put("username", etUsername.getText());
            signupParams.put("password", etPassword.getText());
            signupParams.put("contact", etContact.getText());
            signupParams.put("fullName", etFullName.getText());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        String url = getString(R.string.endpoint) + "auth/signup";

        findViewById(R.id.btnNext).setOnClickListener(l -> {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    signupParams,
                    response -> {
                        try {
                            response.get("success");
                            Intent intent = new Intent(this, SignupCompleteActivity.class);
                            intent.putExtra("token", (String) response.get("access"));

                            UserData userData = new  UserData();
                            userData.userName = String.valueOf(etUsername.getText());
                            userData.userType = "unknown";
                            userData.accessToken = (String) response.get("access");
                            userData.refreshToken = (String) response.get("refresh");
                            userData.fullName = String.valueOf(etFullName.getText());

                            userDatabase.userDataDAO().addUserData(userData);
                            startActivity(intent);
                        }catch (Exception ex) {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                            try {
                                Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            ;
                            System.out.println(ex.getMessage());
                        }
                    },
                    error -> {
                        System.out.println(error.getMessage());
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonObjectRequest);
        });


    }
}