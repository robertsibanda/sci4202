package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsPrivacyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsPrivacyFragment extends Fragment {

    public SettingsPrivacyFragment() {
        // Required empty public constructor
    }

    public static SettingsPrivacyFragment newInstance(String param1,
                                                      String param2) {
        SettingsPrivacyFragment fragment = new SettingsPrivacyFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings_privacy,
                container, false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        CheckBox checkBoxPrivacy = view.findViewById(R.id.checkPassowrd);

        checkBoxPrivacy.setChecked(!userData.ingoreLogin);

        checkBoxPrivacy.setOnClickListener(l -> {
            if (checkBoxPrivacy.isChecked()) {
                userData.ingoreLogin = false;
            } else {
                userData.ingoreLogin = true;
            }
            userDatabase.userDataDAO().updateUserData(userData);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.navHostFragment, new NewSettings())
                    .commit();


        });
        return view;
    }
}