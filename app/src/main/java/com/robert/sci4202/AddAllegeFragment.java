package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserDatabase;

import org.json.JSONObject;

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


        String accessToken =
                userDatabase.userDataDAO().getAllUserData().get(0).accessToken;

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
                RequestQueue requestQueue =
                        Volley.newRequestQueue(view.getContext());

                String url = getString(R.string.endpoint) + "doctor" +
                        "/allege";
                JSONObject params = new JSONObject();
                try {
                    params.put("person", patient);
                    params.put("allege",
                            etAllegeName.getText().toString());
                    params.put("note",
                            etAllegeNote.getText().toString());
                    params.put("reaction",
                            etAllegeReaction.getText().toString());
                    params.put("authorization",
                            "Bearer " + accessToken);

                } catch (Exception ex) {
                    System.out.println("Params error : " + ex.getMessage());
                }
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                params,
                                response -> {
                                    try {
                                        System.out.println("Response " +
                                                "from add " +
                                                "allegse : " + response);

                                        response.get("success");
                                        Toast.makeText(view.getContext()
                                                , "Allege " +
                                                        "added " +
                                                        "successfully",
                                                Toast.LENGTH_SHORT).show();

                                        PatientInforFragment fragment =
                                                new PatientInforFragment();
                                        fragment.category = "alleges";
                                        fragment.patient = patient;
                                        System.out.println("medicine " +
                                                "clicked");
                                        ShowFragment(fragment);

                                    } catch (Exception ex) {
                                        try {
                                            System.out.println("Error " +
                                                    "(inside " +
                                                    "response) : " + response.get("error"));
                                        } catch (Exception ex1) {
                                            System.out.println("Error : "
                                                    + ex1.getMessage());
                                        }
                                        System.out.println("Error " +
                                                "(alleges" +
                                                " outside" +
                                                " response) : " + ex.getMessage());

                                    }
                                },
                                error -> {
                                    System.out.println("Error (allege " +
                                            "add" +
                                            " error):" +
                                            " " + error.getMessage());
                                }
                        );

                requestQueue.add(jsonObjectRequest);


            }
        });
        return view;
    }
}