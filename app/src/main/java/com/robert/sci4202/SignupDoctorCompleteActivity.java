package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONException;
import org.json.JSONObject;

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
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etProff, etHospital;
        etProff = findViewById(R.id.etProff);
        etHospital = findViewById(R.id.etHospital);

        JSONObject updateParams = new JSONObject();
        UserDatabase userDatabase = UserDatabase.getINSTANCE(this.getApplicationContext());
        try {
            updateParams.put("update", "doctor-proff");
            updateParams.put("profession", etProff.getText());
            updateParams.put("hospital", etHospital.getText());
            updateParams.put("authorization",
                    "Bearer " +  userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        findViewById(R.id.btnNextSignup1).setOnClickListener( l -> {

            String url = getString(R.string.endpoint) + "account/update";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    updateParams,
                    response -> {
                        try {
                            response.get("success");
                            Toast.makeText(this,
                                    "Account Created.\nLogin with your new account",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    },
                    error -> {
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonObjectRequest);
        });

    }
}