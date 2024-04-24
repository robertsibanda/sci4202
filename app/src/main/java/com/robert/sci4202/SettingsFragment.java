package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

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
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        TextView txtPersonName = view.findViewById(R.id.txtPersonName);
        TextView txtPersonContact =
                view.findViewById(R.id.txtPersonContact);

        txtPersonName.setText(userData.fullName);
        txtPersonContact.setText(userData.contact);

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