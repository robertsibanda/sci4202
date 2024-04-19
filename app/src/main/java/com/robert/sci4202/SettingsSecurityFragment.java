package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class SettingsSecurityFragment extends Fragment {

    private String mParam2;

    public SettingsSecurityFragment() {
        // Required empty public constructor
    }

    public static SettingsSecurityFragment newInstance(String param1,
                                                       String param2) {
        SettingsSecurityFragment fragment = new SettingsSecurityFragment();
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
        return inflater.inflate(R.layout.fragment_settings_security,
                container, false);
    }
}