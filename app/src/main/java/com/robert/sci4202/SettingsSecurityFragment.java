package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import androidx.fragment.app.Fragment;


public class SettingsSecurityFragment extends Fragment {


    public SettingsSecurityFragment() {
        // Required empty public constructor
    }


    public static SettingsSecurityFragment newInstance(String param1,
                                                       String param2) {
        SettingsSecurityFragment fragment = new SettingsSecurityFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings_security,
                container, false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        EditText etOldPassword, etNewPassword;
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);

        view.findViewById(R.id.btnSavePassword).setOnClickListener(l -> {
            String oldPasssword, newPassord;
            oldPasssword = etOldPassword.getText().toString();
            newPassord = etNewPassword.getText().toString();
            if (oldPasssword.equals(userData.password)) {
                userData.password = newPassord;
                userDatabase.userDataDAO().updateUserData(userData);
                Toast.makeText(view.getContext(), "Password changed " +
                        "successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(view.getContext(),
                        LoginActivity.class));
            } else {
                Toast.makeText(view.getContext(), "Password is wrong",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}