package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorPatientCareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorPatientCareFragment extends Fragment {

    public String patientID = null;
    public String fullname = null;

    public String contact = null;

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

        TextView txtName =
                view.findViewById(R.id.txtPatientName);
        TextView txtContact =
                view.findViewById(R.id.txtPatientContact);

        txtName.setText(fullname);
        txtContact.setText(contact);


        view.findViewById(R.id.btnPatientPrescription).setOnClickListener(l -> {
            PatientInforFragment fragment = new PatientInforFragment();
            fragment.category = "prescriptions";
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