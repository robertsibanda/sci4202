package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.SettingsCategory;
import com.robert.sci4202.recyclerviews.SettingsRecyclerViewAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewSettings extends Fragment {

    public NewSettings() {
        // Required empty public constructor
    }

    public static NewSettings newInstance(String param1, String param2) {
        NewSettings fragment = new NewSettings();
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
        View view = inflater.inflate(R.layout.fragment_new_settings,
                container, false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);


        TextView txtUserFullName = view.findViewById(R.id.txtUserFullName);
        TextView txUserContact = view.findViewById(R.id.txtUsercontact);

        txtUserFullName.setText(userData.fullName);
        txUserContact.setText(userData.contact);

        RecyclerView recyclerView =
                view.findViewById(R.id.recviewSettingsCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        SettingsRecyclerViewAdapter settingsRecyclerViewAdapter =
                new SettingsRecyclerViewAdapter();
        ArrayList<SettingsCategory> settingsCategories = new ArrayList<>();
        settingsCategories.add(new SettingsCategory("Share", "qr"
        ));
        if (userData.userType.equals("patient")) {

            settingsCategories.add(new SettingsCategory("Shared",
                    "shared"));
            settingsCategories.add(new SettingsCategory("History",
                    "history"));
        }
        settingsCategories.add(new SettingsCategory("Privacy", "privacy"));
        settingsCategories.add(new SettingsCategory("Security", "security"
        ));


        FragmentManager fragmentManager = getParentFragmentManager();
        settingsRecyclerViewAdapter.fragmentManager = fragmentManager;
        settingsRecyclerViewAdapter.setSettingsCategories(settingsCategories);
        recyclerView.setAdapter(settingsRecyclerViewAdapter);
        return view;
    }
}