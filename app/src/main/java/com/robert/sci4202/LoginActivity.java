package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(this.getApplicationContext());


                    JSONObject params = new JSONObject();
                    // on below line we are passing our key
                    // and value pair to our parameters.
                    try {
                        params.put("username", etUsername.getText().toString());
                        params.put("password", etPassword.getText().toString());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    String url = getString(R.string.endpoint) + "login";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                           params,
                           response -> {
                               try {
                                   System.out.println(response);
                                   String accessToken = response.getString("access");
                                   String refreshToken = response.getString("refresh");
                                   String fullName = response.getString("fullName");
                                   String userName = response.getString("user");
                                   String userType = response.getString("userType");

                                   System.out.println("Response from server : " + response);

                                   UserDatabase userDatabase = UserDatabase.getINSTANCE(this.getApplicationContext());
                                   UserData userData = new  UserData();
                                   userData.userName = userName;
                                   userData.userType = userType;
                                   userData.accessToken = accessToken;
                                   userData.refreshToken = refreshToken;
                                   userData.fullName = fullName;

                                   userDatabase.userDataDAO().addUserData(userData);

                                   startActivity(new Intent(this.getApplicationContext(), MainActivity.class));

                               } catch (JSONException e) {
                                   try {
                                       Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show();
                                   } catch (JSONException ex) {
                                       System.out.println(ex.getMessage());
                                   }
                               }
                           },
                            error -> {
                                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                    requestQueue.add(jsonObjectRequest);
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