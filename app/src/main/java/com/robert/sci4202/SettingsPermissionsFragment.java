package com.robert.sci4202;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.robert.sci4202.objects.Permission;
import com.robert.sci4202.recyclerviews.PermisionsRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsPermissionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsPermissionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsPermissionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsPermissionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsPermissionsFragment newInstance(String param1, String param2) {
        SettingsPermissionsFragment fragment = new SettingsPermissionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings_permissions, container, false);
        PermisionsRecyclerViewAdapter permisionsRecyclerViewAdapter =
                new PermisionsRecyclerViewAdapter();

        CheckBox chkDoctorProfileVisibility = view.findViewById(R.id.chkDoctorProfileVisibility);
        CheckBox chkDoctorAddEvent = view.findViewById(R.id.chkDoctorAddEvent);
        CheckBox chkDoctorAddMe = view.findViewById(R.id.chkDoctorAddMe);
        //chkDoctorProfileVisibility.setOnCheckedChangeListener( l -> {});
        return view;
    }
}