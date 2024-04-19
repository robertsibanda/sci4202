package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorPatientCareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorPatientCareFragment extends Fragment {

    public String patientID = null;

    public DoctorPatientCareFragment() {
        // Required empty public constructor
    }

    public static DoctorPatientCareFragment newInstance(String param1,
                                                        String param2) {
        DoctorPatientCareFragment fragment =
                new DoctorPatientCareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void ShowFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.viewPatientInforNav, fragment)
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =
                inflater.inflate(R.layout.fragment_doctor_patient_care,
                        container, false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        List<UserData> userData =
                userDatabase.userDataDAO().getAllUserData();

        RequestQueue requestQueue =
                Volley.newRequestQueue(view.getContext());
        String url = getString(R.string.endpoint) + "basic/profile";

        JSONObject params = new JSONObject();
        try {
            params.put("username", patientID);
            params.put("authorization",
                    "Bearer " + userDatabase.userDataDAO()
                            .getAllUserData().get(0).accessToken);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                response -> {
                    try {
                        JSONObject person =
                                response.getJSONObject("person");
                        String fullName = person.getString(
                                "fullname");

                        String contact = person.getString("contact");

                        TextView txtName =
                                view.findViewById(R.id.txtPatientName);
                        TextView txtContact =
                                view.findViewById(R.id.txtPatientContact);

                        txtName.setText(fullName);
                        txtContact.setText(contact);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    System.out.println("Error : " + error.getMessage());
                });

        requestQueue.add(jsonObjectRequest);


        view.findViewById(R.id.btnPatientMedicine).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "medicine";
            fragment.patient = patientID;
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnPatientNotes).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "notes";
            fragment.patient = patientID;
            System.out.println("Notes clicked");
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnPatientDiseases).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "disease";
            fragment.patient = patientID;
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnPatientDoctors).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "doctors";
            fragment.patient = patientID;
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnPatientAllegies).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "alleges";
            fragment.patient = patientID;
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnPatientLabResults).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "results";
            fragment.patient = patientID;
            ShowFragment(fragment);
        });

        view.findViewById(R.id.btnAddAppointment).setOnClickListener(l -> {
            System.out.println("opening doctor patient care fragment");
            AddAppointmentFragment fragment = new AddAppointmentFragment();
            fragment.person = patientID;
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, fragment)
                    .commit();
        });


        return view;
    }
}