package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;


public class AddAllegeFragment extends Fragment {
    public String patient;

    public AddAllegeFragment() {
        // Required empty public constructor
    }

    public static AddAllegeFragment newInstance(String param1,
                                                String param2) {
        AddAllegeFragment fragment = new AddAllegeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void ShowFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
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
        View view = inflater.inflate(R.layout.fragment_add_allege,
                container, false);

        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        EditText etAllegeName =
                view.findViewById(R.id.etAllegeName);
        EditText etAllegeReaction =
                view.findViewById(R.id.etAllegeRaction);

        EditText etAllegeNote =
                view.findViewById(R.id.etAllegeNote);

        view.findViewById(R.id.btnSaveAllege).setOnClickListener(l -> {

            if (etAllegeName.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Allege is required",
                        Toast.LENGTH_SHORT).show();
            } else if (etAllegeReaction.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Reaction is required"
                        , Toast.LENGTH_SHORT).show();
            } else if (etAllegeNote.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Note is required",
                        Toast.LENGTH_SHORT).show();
            } else {
                new Thread(() -> {

                    Map<String, Object> params = new HashMap<>();
                    try {
                        params.put("patient", patient);
                        params.put("doctor", userData.userID);
                        params.put("type", "allege");
                        params.put("allege",
                                etAllegeName.getText().toString());
                        params.put("reaction",
                                etAllegeReaction.getText().toString());
                        params.put("note",
                                etAllegeNote.getText().toString());
                        params.put("date", new Date().toLocaleString());
                    } catch (Exception ex) {
                        System.out.println("Params error : " + ex.getMessage());
                    }

                    try {
                        ServerResult result = RPCRequests.sendRequest(
                                "update_records",
                                params);
                        System.out.println("Result : " + result.getResult());
                        try {
                            result.getResult().get("success");
                        } catch (Exception ex) {
                            try {
                                Toast.makeText(view.getContext(),
                                        result.getResult().get("error").toString(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println(result.getResult());

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                //Do something on UiThread
                                PatientInforFragment fragment =
                                        new PatientInforFragment();
                                fragment.category = "alleges";
                                fragment.patient = patient;
                                System.out.println("Results clicked");
                                ShowFragment(fragment);
                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }).start();

            }
        });
        return view;
    }
}