package com.robert.sci4202;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.DoctorAppointment;
import com.robert.sci4202.objects.MyDoctorItem;
import com.robert.sci4202.objects.Patient;
import com.robert.sci4202.recyclerviews.DoctorAppointmentRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.MyDoctorRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.PatientRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container,
                false);
        EditText etSearchText = view.findViewById(R.id.etSearchText);


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        String fullName = userData.fullName;

        if (Objects.equals(userData.userType, "doctor")) {
            etSearchText.setHint("search patient");

            TextView txtFullName = view.findViewById(R.id.txtFullName);
            String txt = "Welcome Dr " + fullName;
            txtFullName.setText(txt);
        } else {
            TextView txtFullName = view.findViewById(R.id.txtFullName);
            String txt = "Welcome " + fullName;
            txtFullName.setText(txt);
        }


        RoundedImageView roundedImageView =
                view.findViewById(R.id.imgUserProfile);

      /*  Glide.with(view)
                .asBitmap()
                .load(getString(R.string.endpoint) + "image/user/" +
                userData.get(0).userName) //TODO fix this in backed code
                .into(roundedImageView);
                */

        RecyclerView recyclerView = view.findViewById(R.id.recviewHome);

        RecyclerView recyclerViewNotifications =
                view.findViewById(R.id.recViewNotifications);

        etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i,
                                          KeyEvent keyEvent) {

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                String search_string =
                        String.valueOf(etSearchText.getText());
                String search_type = null;
                UserDatabase userDatabase =
                        UserDatabase.getINSTANCE(view.getContext());
                String userType =
                        userDatabase.userDataDAO().getAllUserData().get(0).userType;

                if (Objects.equals(userType, "doctor")) {
                    search_type = "patient";
                } else {
                    search_type = "doctor";
                }

                Map<String, String> params = new HashMap<>();
                try {
                    params.put("search_string", search_string);
                    params.put("user_type", search_type);
                    params.put("signed", "signed");
                    params.put("userid", userData.userID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String finalSearch_type = search_type;
                MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter
                        = new MyDoctorRecyclerviewAdapter();
                myDoctorRecyclerviewAdapter.fragmentManager =
                        getParentFragmentManager();

                new Thread(() -> {
                    try {
                        ServerResult result = RPCRequests.sendRequest(
                                "search_person",
                                params);
                        System.out.println("Result : " + result.getResult());
                        try {
                            result.getResult().get("success");
                        } catch (Exception ex) {
                            try {
                                Toast.makeText(view.getContext(),
                                        result.getResult().get("error").toString(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println(result.getResult());
                        JSONArray people =
                                result.getResult().getJSONArray("success");
                        if (finalSearch_type.equals("doctor")) {
                            ArrayList<MyDoctorItem> myDoctorItems =
                                    new ArrayList<>();
                            for (int num = 0; num < people.length(); num++) {
                                JSONObject person =
                                        people.getJSONObject(num);
                                String fullname = person.getString(
                                        "fullname");
                                String contact = person.getString(
                                        "contact");

                                String organistion = person.getString(
                                        "organisation");
                                boolean canView = person.getBoolean(
                                        "view");
                                boolean canUpdate = person.getBoolean(
                                        "update");

                                String occupation = person.getString(
                                        "occupation");

                                String userid = person.getString("userid");

                                String biography = person.getString("bio");

                                myDoctorItems.add(new MyDoctorItem(fullname, "", contact, occupation, organistion, userid, canView, canUpdate, biography));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                                    myDoctorRecyclerviewAdapter.frag =
                                            "care";
                                    recyclerView.setAdapter(myDoctorRecyclerviewAdapter);
                                }
                            });


                        } else {
                            PatientRecyclerviewAdapter patientRecyclerviewAdapter = new PatientRecyclerviewAdapter(view.getContext(), getParentFragmentManager());
                            ArrayList<Patient> patients =
                                    new ArrayList<>();
                            for (int num = 0; num < people.length(); num++) {
                                JSONObject patient =
                                        people.getJSONObject(num);
                                String fullname = patient.getString(
                                        "fullname");
                                String contact = patient.getString(
                                        "contact");

                                boolean canView = patient.getBoolean(
                                        "view");
                                boolean canUpdate = patient.getBoolean(
                                        "update");

                                String userid = patient.getString(
                                        "userid");

                                patients.add(new Patient("", contact,
                                        userid, fullname, canView,
                                        canUpdate));
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    patientRecyclerviewAdapter.setPatients(patients);
                                    recyclerView.setAdapter(patientRecyclerviewAdapter);
                                }
                            });

                        }

                    } catch (Exception e) {
                        System.out.println("Error : " + e.getMessage());
                    }
                }).start();

                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        new Thread(() -> {
            //get user notifications
        }).start();


        new Thread(() -> {

            Map<String, String> params = new HashMap<>();


            Date theDate = new Date();

            int currentMonth, currentYear;
            String currentDate;

            currentYear = theDate.getYear();
            currentMonth = theDate.getMonth();
            currentDate = String.valueOf(theDate.getDate());


            String date_key =
                    currentDate + currentMonth + currentYear;


            params.put("userid", userData.userID);
            params.put("user_type", userData.userType);
            params.put("date", date_key);

            //get user appointments
            try {
                ServerResult result = RPCRequests.sendRequest(
                        "get_appointments",
                        params);
                System.out.println("Appointments : " + result.getResult());
                try {
                    result.getResult().get("success");
                } catch (Exception ex) {
                    try {
                        Toast.makeText(view.getContext(),
                                result.getResult().get("error").toString(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                System.out.println(result.getResult());
                DoctorAppointmentRecyclerviewAdapter calenderRecyclerviewAdapter
                        = new DoctorAppointmentRecyclerviewAdapter();
                calenderRecyclerviewAdapter.fragment = this;
                ArrayList<DoctorAppointment> calendars = new ArrayList<>();

                JSONArray appointments =
                        result.getResult().getJSONArray("success");

                System.out.println("User type : " + userData.userType);

                if (Objects.equals(userData.userType, "patient")) {
                    System.out.println("Showing appointments for " +
                            "patients");
                    for (int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment =
                                appointments.getJSONObject(i);
                        String doctorName, doctorProfession,
                                appointmentDate,
                                getAppointmentTime,
                                appointmendDescription, approver,
                                patientID;

                        doctorName = appointment.getString("doctor_name");
                        doctorProfession = appointment.getString(
                                "doctor_proff");

                        appointmentDate = appointment.getString("date");
                        getAppointmentTime = appointment.getString("time");
                        appointmendDescription = appointment.getString(
                                "description");
                        approver = appointment.getString("approver");

                        patientID = appointment.getString("patient");

                        boolean approved = appointment.getBoolean(
                                "approved");
                        boolean rejected = appointment.getBoolean(
                                "rejected");
                        calendars.add(new DoctorAppointment(doctorName,
                                doctorProfession, appointmentDate,
                                getAppointmentTime, appointmendDescription,
                                approver, patientID, approved, rejected));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
                            calenderRecyclerviewAdapter.setDoctorAppointments(calendars);
                            recyclerViewNotifications.setAdapter(calenderRecyclerviewAdapter);
                        }
                    });
                } else {
                    System.out.println("Showing appointments for " +
                            "doctors");
                    for (int i = 0; i < appointments.length(); i++) {
                        JSONObject appointment =
                                appointments.getJSONObject(i);
                        String patientName, appointmentDate,
                                getAppointmentTime, appointmendDescription,
                                patientContact, approver, patientID;

                        patientID = appointment.getString("patient");
                        patientName = appointment.getString(
                                "patient_name");
                        patientContact = appointment.getString(
                                "patient_contact");

                        appointmentDate = appointment.getString("date");
                        getAppointmentTime = appointment.getString("time");
                        appointmendDescription = appointment.getString(
                                "description");
                        approver = appointment.getString("approver");

                        boolean approved = appointment.getBoolean(
                                "approved");

                        boolean rejected = appointment.getBoolean(
                                "rejected");

                        calendars.add(new DoctorAppointment(patientName,
                                patientContact, appointmentDate,
                                getAppointmentTime, appointmendDescription,
                                approver, patientID, approved, rejected));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
                            calenderRecyclerviewAdapter.setDoctorAppointments(calendars);
                            recyclerViewNotifications.setAdapter(calenderRecyclerviewAdapter);
                        }
                    });
                }


            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }).start();

        return view;
    }
}