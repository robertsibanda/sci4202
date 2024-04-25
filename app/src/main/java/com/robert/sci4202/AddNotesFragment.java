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

public class AddNotesFragment extends Fragment {

    public String patient;
    public String docName;

    public AddNotesFragment() {
        // Required empty public constructor
    }

    public static AddNotesFragment newInstance(String param1,
                                               String param2) {
        AddNotesFragment fragment = new AddNotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void ShowFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.viewPatientInforNav, fragment)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_notes,
                container, false);

        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);


        System.out.println("Infor from fragment : " + patient + " -> " + docName);

        EditText etNotesText = view.findViewById(R.id.etNoteText);


        view.findViewById(R.id.btnSaveNote).setOnClickListener(l -> {

            if (etNotesText.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Note must be more " +
                        "that 10 characters", Toast.LENGTH_SHORT).show();
            } else {

                new Thread(() -> {

                    Map<String, String> params = new HashMap<>();

                    params.put("type", "notes");
                    params.put("patient", patient);
                    params.put("date", new Date().toLocaleString());
                    params.put("content",
                            etNotesText.getText().toString());
                    params.put("doctor", userData.userID);
                    params.put("author", userData.fullName);

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
                                fragment.category = "notes";
                                fragment.patient = patient;
                                System.out.println("Notes clicked");
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