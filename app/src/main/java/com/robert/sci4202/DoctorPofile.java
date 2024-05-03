package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.AppointmentDay;
import com.robert.sci4202.recyclerviews.AppointmentDayRecyclerviewAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DoctorPofile extends Fragment {

    public String selectedTime = null;
    public String doctorID, proffession, contact, bio, fullname;


    public DoctorPofile() {
        // Required empty public constructor
    }

    public static DoctorPofile newInstance(String param1, String param2) {
        DoctorPofile fragment = new DoctorPofile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void clearSelected(Button[] timeButtons, Button button) {
        for (Button b :
                timeButtons) {
            if (!b.getText().equals(button.getText())) {
                b.setBackgroundColor(getResources().getColor(R.color.grey));
            } else {
                b.setBackgroundColor(getResources().getColor(R.color.clicked_data));
                selectedTime = b.getText().toString();
            }
        }
    }


    public void ShowFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_pofile,
                container, false);

        EditText etAppointmentDescription =
                view.findViewById(R.id.etAppointmentDescription);

        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);


        TextView txtFullname, txtProfession, txtcontact, txtbio;
        txtFullname = view.findViewById(R.id.txtDoctorFullName);
        txtProfession = view.findViewById(R.id.txtDoctorProfession);
        txtcontact = view.findViewById(R.id.txtDoctorContact);
        txtbio = view.findViewById(R.id.txtDoctorbio);

        txtFullname.setText(fullname);
        txtProfession.setText(proffession);
        txtcontact.setText(contact);
        txtbio.setText(bio);

        Button btn9, btn10, btn11, btn12, btn13, btn14, btn15;


        btn9 = view.findViewById(R.id.btntime9);
        btn10 = view.findViewById(R.id.btntime10);
        btn11 = view.findViewById(R.id.btntime11);
        btn12 = view.findViewById(R.id.btntime12);
        btn13 = view.findViewById(R.id.btntime13);
        btn14 = view.findViewById(R.id.btntime14);

        Button[] timeButtons = {btn9, btn10, btn11, btn12, btn13, btn14};
        for (Button b : timeButtons
        ) {
            b.setOnClickListener(l -> clearSelected(timeButtons, b));

        }

        view.findViewById(R.id.btnBack).setOnClickListener(l -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, new HomeFragment())
                    .commit();
        });

        RecyclerView recviewDateSchedule =
                view.findViewById(R.id.recviewDateSchedule);
        recviewDateSchedule.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        ArrayList<AppointmentDay> appointmentDays = new ArrayList<>();
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE, d");

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            String formattedDate = date.format(formatter);

            String[] foundDate = formattedDate.split(",");

            appointmentDays.add(new AppointmentDay(foundDate[0],
                    foundDate[1], false));
        }

        AppointmentDayRecyclerviewAdapter appointmentDayRecyclerviewAdapter = new AppointmentDayRecyclerviewAdapter();
        appointmentDayRecyclerviewAdapter.parentView = view;
        appointmentDayRecyclerviewAdapter.setAppointmentDays(appointmentDays);
        recviewDateSchedule.setAdapter(appointmentDayRecyclerviewAdapter);

        view.findViewById(R.id.btnBookAppointment).setOnClickListener(l -> {

            View selectedView = view.findViewWithTag("selected");
            TextView txtDate = selectedView.findViewById(R.id.txtDate);
            String selected = txtDate.getText().toString();

            System.out.println("Selected Date : " + selected + " time : "
                    + selectedTime);

            Date theDate = new Date();

            int currentMonth, currentYear;

            currentYear = theDate.getYear();
            currentMonth = theDate.getMonth(;

            System.out.println("Current year : " + currentYear);
            if ((theDate.getDate() > 24) && (Integer.parseInt(selected.trim()) < 24)) {
                // date is of next month
                currentMonth += 1;
                System.out.println("Month selected is next month");
            }


            String date_key =
                    selected + currentMonth + currentYear;

            String date =
                    selected + "-" + (currentMonth + 1) + "-" + (currentYear + 1900);
            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("creator", userData.userID);
                params.put("patient", userData.userID);
                params.put("time", selectedTime.split(" ")[0]);
                params.put("date", date.trim());
                params.put("date_key", date_key.trim());
                params.put("doctor_name", fullname);
                params.put("doctor_proff", proffession);
                params.put("doctor",
                        doctorID);
                params.put("patient_name", userData.fullName);
                params.put("patient_contact", userData.contact);
                params.put("description",
                        etAppointmentDescription.getText().toString());

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "create_appointment",
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

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            ShowFragment(new HomeFragment());
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();


        });
        return view;
    }

}