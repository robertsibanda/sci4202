package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONObject;

import androidx.fragment.app.Fragment;


public class AddAppointmentFragment extends Fragment {

    public String person;

    public AddAppointmentFragment() {
        // Required empty public constructor
    }


    public static AddAppointmentFragment newInstance(String param1,
                                                     String param2) {
        AddAppointmentFragment fragment = new AddAppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_appointment,
                container, false);

        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        String accessToken =
                userDatabase.userDataDAO().getAllUserData().get(0).accessToken;


        System.out.println("Adding appointment with patient : " + person);
        DatePicker datePicker =
                view.findViewById(R.id.dateAppointmentDate);
        TimePicker timePicker =
                view.findViewById(R.id.timeAppointmentTime);
        EditText appointmntDescription =
                view.findViewById(R.id.etAppointmentDescription);

        view.findViewById(R.id.btnSaveAppointment).setOnClickListener(l -> {
            int month = datePicker.getMonth();
            int dayOfmnth = datePicker.getDayOfMonth();
            int year = datePicker.getYear();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());

            if (appointmntDescription.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Description is " +
                        "requireed", Toast.LENGTH_SHORT).show();
            } else {

                String url = getString(R.string.endpoint) + "doctor" +
                        "/appointment";
                JSONObject params = new JSONObject();
                try {
                    params.put("person", person);
                    params.put("year",
                            year);
                    params.put("month",
                            month);
                    params.put("day",
                            dayOfmnth);

                    params.put("description",
                            appointmntDescription.getText().toString());
                    params.put("hour",
                            hour);
                    params.put("minute",
                            minute);
                    params.put("authorization",
                            "Bearer " + accessToken);

                } catch (Exception ex) {
                    System.out.println("Params error : " + ex.getMessage());
                }
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                params,
                                response -> {
                                    try {
                                        System.out.println("Response " +
                                                "from add " +
                                                "appointment : " + response);

                                        response.get("success");
                                        Toast.makeText(view.getContext()
                                                , "Appointment " +
                                                        "added " +
                                                        "successfully",
                                                Toast.LENGTH_SHORT).show();

                                        DoctorPatientCareFragment fragment = new DoctorPatientCareFragment();
                                        fragment.patientID = person;
                                        getParentFragmentManager().beginTransaction()
                                                .replace(R.id.navHostFragment, fragment)
                                                .commit();


                                    } catch (Exception ex) {
                                        try {
                                            Toast.makeText(view.getContext(), response.get("error").toString(),
                                                    Toast.LENGTH_SHORT).show();
                                            System.out.println("Error " +
                                                    "(inside " +
                                                    "response) : " + response.get("error"));
                                        } catch (Exception ex1) {
                                            System.out.println("Error : "
                                                    + ex1.getMessage());
                                        }
                                        System.out.println("Error " +
                                                "(appointment" +
                                                " outside" +
                                                " response) : " + ex.getMessage());

                                    }
                                },
                                error -> {
                                    System.out.println("Error (notes " +
                                            "appointment" +
                                            " error):" +
                                            " " + error.getMessage());
                                }
                        );

                requestQueue.add(jsonObjectRequest);


            }

        });
        return view;
    }
}