package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

public class ScanQR extends Fragment {


    String foundString = null;

    public ScanQR() {
        // Required empty public constructor
    }


    public static ScanQR newInstance(String param1, String param2) {
        ScanQR fragment = new ScanQR();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_q_r, container,
                false);
        view.findViewById(R.id.btnQRScanner).setOnClickListener(l -> {
            try {
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setPrompt("Volume Up to Flash ON");
                scanOptions.setBeepEnabled(true);
                scanOptions.setOrientationLocked(true);
                scanOptions.setCaptureActivity(Capture.class);
                barLaucher.launch(scanOptions);

            } catch (Exception ex) {
                Toast.makeText(view.getContext(), ex.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    ActivityResultLauncher<ScanOptions> barLaucher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String[] patientData = result.getContents().split(",");
                    if (patientData.length == 3) {
                        String patientID = patientData[0];
                        String patientName = patientData[1];
                        String patientContact = patientData[2];

                        DoctorPatientCareFragment doctorPatientCareFragment =
                                new DoctorPatientCareFragment();
                        Toast.makeText(this.getContext(),
                                result.getContents(), Toast.LENGTH_LONG).show();
                        doctorPatientCareFragment.contact = patientContact;
                        doctorPatientCareFragment.fullname = patientName;
                        doctorPatientCareFragment.patientID = patientID;
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.navHostFragment,
                                        doctorPatientCareFragment)
                                .commit();
                    } else {
                        Toast.makeText(this.getContext(), "Invalid data"
                                , Toast.LENGTH_SHORT).show();
                    }

                }
            });
}