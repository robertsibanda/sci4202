package com.robert.sci4202;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SignupCompleteActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_complete);
        context = this.getApplicationContext();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button next = findViewById(R.id.btnNextSignup2);

        RadioGroup radioGroup = findViewById(R.id.radioUserType);
        RadioButton radioPatient = findViewById(R.id.radioPatient);
        RadioButton radioDoctor = findViewById(R.id.radiodoctor);

        CheckBox chkDoctorAddME = findViewById(R.id.chkDoctorAddMe);
        CheckBox chkDoctorAddEvent = findViewById(R.id.chkDoctorAddEvent);
        CheckBox chkProfileVisibility = findViewById(R.id.chkDoctorProfileVisibility);

        JSONObject updateParams = new JSONObject();

        UserDatabase userDatabase = UserDatabase.getINSTANCE(context);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                System.out.println(" CHECK " + rb.getText().toString() == "Patient");
                if (rb.getText().toString().equals("Doctor")) {


                    chkDoctorAddME.setVisibility(View.INVISIBLE);
                    chkDoctorAddEvent.setVisibility(View.INVISIBLE);
                    chkProfileVisibility.setVisibility(View.INVISIBLE);
                    chkDoctorAddME.setVisibility(View.GONE);
                    chkDoctorAddEvent.setVisibility(View.GONE);
                    chkProfileVisibility.setVisibility(View.GONE);
                    try{


                        updateParams.put("updateValue", "doctor");
                        updateParams.put("update", "userType");
                        updateParams.put("authorization",
                                "Bearer " +  userDatabase.userDataDAO().getAllUserData().get(0).accessToken);

                    }catch (Exception ex) {
                        Toast.makeText(SignupCompleteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else if (rb.getText().toString().equals("Patient")) {
                    chkDoctorAddME.setVisibility(View.VISIBLE);
                    chkDoctorAddEvent.setVisibility(View.VISIBLE);
                    chkProfileVisibility.setVisibility(View.VISIBLE);

                    chkDoctorAddME.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            chkDoctorAddME.setChecked(b);
                        }
                    });


                    chkDoctorAddME.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            chkDoctorAddME.setChecked(b);
                        }
                    });


                    chkDoctorAddEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            chkDoctorAddEvent.setChecked(b);
                        }
                    });

                    try {

                        updateParams.put("update", "userType");
                        updateParams.put("updateValue", "patient");
                        updateParams.put("accountPublic", chkProfileVisibility.isChecked());
                        updateParams.put("patientPublic", chkDoctorAddME.isChecked());
                        updateParams.put("patientCalender", chkDoctorAddEvent.isChecked());
                        updateParams.put("authorization",
                                "Bearer " +  userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                    }catch (Exception ex) {

                        Toast.makeText(SignupCompleteActivity.this,
                                ex.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        next.setOnClickListener(l -> {
            if (radioPatient.isChecked() || radioDoctor.isChecked()) {
                RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        getString(R.string.endpoint) + "account/update",
                        updateParams,
                        response -> {
                            try {
                                response.get("success");
                                Toast.makeText(this,
                                        "Account Created.\nLogin with your new account",
                                        Toast.LENGTH_LONG).show();

                                if (updateParams.get("updateValue") == "doctor") {
                                    Intent intent = new Intent(this, SignupDoctorCompleteActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(this, LoginActivity.class);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                try {
                                    Toast.makeText(this, response.getString("error"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException ex) {
                                    System.out.println("Error updating : " + ex.getMessage());
                                }
                                throw new RuntimeException(e);
                            }

                        },
                        error -> {
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                requestQueue.add(jsonObjectRequest);
            }
            else{
                Toast.makeText(this,
                        "Are you a Doctor\nOr a Patient",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}