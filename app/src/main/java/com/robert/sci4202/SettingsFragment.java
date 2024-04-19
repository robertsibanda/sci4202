package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.List;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1,
                                               String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings,
                container, false);


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        List<UserData> userData =
                userDatabase.userDataDAO().getAllUserData();


        view.findViewById(R.id.btnPermissions).setOnClickListener(l -> {
            //TODO Delete Account
        });


        view.findViewById(R.id.btnLogout).setOnClickListener(l -> {
            userDatabase.userDataDAO().deleteAll();

            startActivity(new Intent(view.getContext(),
                    LoginActivity.class));
        });
        return view;
    }
}