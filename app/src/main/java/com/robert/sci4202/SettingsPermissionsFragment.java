package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsPermissionsFragment#newInstance} factory
 * method to
 * create an instance of this fragment.
 */
public class SettingsPermissionsFragment extends Fragment {

    public SettingsPermissionsFragment() {
        // Required empty public constructor
    }

    public static SettingsPermissionsFragment newInstance(String param1,
                                                          String param2) {
        SettingsPermissionsFragment fragment =
                new SettingsPermissionsFragment();
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
        View view =
                inflater.inflate(R.layout.fragment_settings_permissions,
                        container, false);

        CheckBox chkDoctorProfileVisibility =
                view.findViewById(R.id.chkDoctorProfileVisibility);
        CheckBox chkDoctorAddEvent =
                view.findViewById(R.id.chkDoctorAddEvent);
        CheckBox chkDoctorAddMe = view.findViewById(R.id.chkDoctorAddMe);
        //chkDoctorProfileVisibility.setOnCheckedChangeListener( l -> {});
        return view;
    }
}