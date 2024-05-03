package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.DoctorAppointment;
import com.robert.sci4202.recyclerviews.DoctorAppointmentRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalenderFragment extends Fragment {


    public CalenderFragment() {
        // Required empty public constructor
    }

    public static CalenderFragment newInstance(String param1,
                                               String param2) {
        CalenderFragment fragment = new CalenderFragment();
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

        Fragment currentFragment = this;

        View view = inflater.inflate(R.layout.fragment_calender,
                container, false);

        RecyclerView recyclerView =
                view.findViewById(R.id.recviewCarlender);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        CalendarView calendarView = view.findViewById(R.id.datePicker);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int day) {

                Calendar c = Calendar.getInstance();
                c.set(year, month, day);

                new Thread(() -> {

                    String date_key =
                            day + "" + month + "" + (year - 1900);

                    DoctorAppointmentRecyclerviewAdapter calenderRecyclerviewAdapter
                            =
                            new DoctorAppointmentRecyclerviewAdapter();
                    calenderRecyclerviewAdapter.fragment = currentFragment;

                    Map<String, String> params = new HashMap<>();


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

                        ArrayList<DoctorAppointment> calendars =
                                new ArrayList<>();

                        JSONArray appointments =
                                result.getResult().getJSONArray("success");

                        System.out.println("User type : " + userData.userType);

                        if (Objects.equals(userData.userType, "patient")) {
                            System.out.println("Showing appointments for" +
                                    " " +
                                    "patients");
                            for (int i = 0; i < appointments.length(); i++) {
                                JSONObject appointment =
                                        appointments.getJSONObject(i);
                                String doctorName, doctorProfession,
                                        appointmentDate,
                                        getAppointmentTime,
                                        appointmendDescription, approver,
                                        patientID;

                                doctorName = appointment.getString(
                                        "doctor_name");
                                doctorProfession = appointment.getString(
                                        "doctor_proff");

                                appointmentDate = appointment.getString(
                                        "date");
                                getAppointmentTime =
                                        appointment.getString("time");
                                appointmendDescription =
                                        appointment.getString(
                                                "description");
                                approver = appointment.getString(
                                        "approver");

                                patientID = appointment.getString(
                                        "patient");

                                boolean approved = appointment.getBoolean(
                                        "approved");
                                boolean rejected = appointment.getBoolean(
                                        "rejected");
                                calendars.add(new DoctorAppointment(doctorName,
                                        doctorProfession, appointmentDate,
                                        getAppointmentTime,
                                        appointmendDescription,
                                        approver, patientID, approved,
                                        rejected));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
                                    calenderRecyclerviewAdapter.setDoctorAppointments(calendars);
                                    recyclerView.setAdapter(calenderRecyclerviewAdapter);
                                }
                            });
                        } else {
                            System.out.println("Showing appointments for" +
                                    " " +
                                    "doctors");
                            for (int i = 0; i < appointments.length(); i++) {
                                JSONObject appointment =
                                        appointments.getJSONObject(i);
                                String patientName, appointmentDate,
                                        getAppointmentTime,
                                        appointmendDescription,
                                        patientContact, approver,
                                        patientID;

                                patientID = appointment.getString(
                                        "patient");
                                patientName = appointment.getString(
                                        "patient_name");
                                patientContact = appointment.getString(
                                        "patient_contact");

                                appointmentDate = appointment.getString(
                                        "date");
                                getAppointmentTime =
                                        appointment.getString("time");
                                appointmendDescription =
                                        appointment.getString(
                                                "description");
                                approver = appointment.getString(
                                        "approver");

                                boolean approved = appointment.getBoolean(
                                        "approved");

                                boolean rejected = appointment.getBoolean(
                                        "rejected");

                                calendars.add(new DoctorAppointment(patientName,
                                        patientContact, appointmentDate,
                                        getAppointmentTime,
                                        appointmendDescription,
                                        approver, patientID, approved,
                                        rejected));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
                                    calenderRecyclerviewAdapter.setDoctorAppointments(calendars);
                                    recyclerView.setAdapter(calenderRecyclerviewAdapter);
                                }
                            });
                        }


                    } catch (Exception e) {
                        System.out.println("Error : " + e.getMessage());
                    }
                }).start();

            }
        });
        return view;
    }
}