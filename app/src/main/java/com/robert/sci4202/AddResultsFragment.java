package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONObject;

import androidx.fragment.app.Fragment;


public class AddResultsFragment extends Fragment {

    public String patient;

    public String doctor;

    public AddResultsFragment() {
        // Required empty public constructor
    }

    public static AddResultsFragment newInstance(String param1, String param2) {
        AddResultsFragment fragment = new AddResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void ShowFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.viewPatientInforNav, fragment)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_results, container, false);
        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        System.out.println("Infor from fragment : " + patient + " -> " + doctor);

        String accessToken = userDatabase.userDataDAO().getAllUserData().get(0).accessToken;

        EditText etTestName = view.findViewById(R.id.etTestName);
        EditText etTestCode = view.findViewById(R.id.etTestCode);
        EditText etResultName = view.findViewById(R.id.etResultName);
        EditText etResultCode = view.findViewById(R.id.etResultCode);


        view.findViewById(R.id.btnSaveResult).setOnClickListener(l -> {

            if (etResultName.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Medicine must be more that 10 characters", Toast.LENGTH_SHORT).show();
            }
            else if (etResultCode.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Qty must be more that 10 characters", Toast.LENGTH_SHORT).show();
            }

            else if (etTestCode.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Qty must be more that 10 characters", Toast.LENGTH_SHORT).show();
            }
            else if (etTestName.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Qty must be more that 10 characters", Toast.LENGTH_SHORT).show();
            }
            else {
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

                String url = getString(R.string.endpoint) + "doctor/result";
                JSONObject params = new JSONObject();
                try {
                    params.put("person", patient);
                    params.put("test", etTestName.getText().toString());
                    params.put("test_code", etTestCode.getText().toString());
                    params.put("result", etResultName.getText().toString());
                    params.put("result_code", etResultCode.getText().toString());
                    params.put("authorization",
                            "Bearer " +  accessToken);

                }catch(Exception ex) {
                    System.out.println("Params error : " + ex.getMessage());
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        params,
                        response -> {
                            try {
                                System.out.println("Response from add notes : " + response);

                                response.get("success");
                                Toast.makeText(view.getContext(), "Note added successfully",
                                        Toast.LENGTH_SHORT).show();

                                PatientInforFragment fragment = new PatientInforFragment();
                                fragment.category  = "results";
                                fragment.patient = patient;
                                System.out.println("medicine clicked");
                                ShowFragment(fragment);

                            }catch (Exception ex) {
                                try {
                                    System.out.println("Error (inside response) : " + response.get("error"));
                                }
                                catch (Exception ex1) {
                                    System.out.println("Error : " + ex1.getMessage());
                                }
                                System.out.println("Error (notes outside response) : " + ex.getMessage());

                            }
                        },
                        error -> {
                            System.out.println("Error (notes add error): " + error.getMessage());
                        }
                );

                requestQueue.add(jsonObjectRequest);


            }
        });
        return view;
    }
}