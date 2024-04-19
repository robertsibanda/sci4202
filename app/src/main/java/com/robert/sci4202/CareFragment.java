package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.Allege;
import com.robert.sci4202.objects.LabResult;
import com.robert.sci4202.objects.MyDoctorItem;
import com.robert.sci4202.objects.Note;
import com.robert.sci4202.objects.Prescription;
import com.robert.sci4202.recyclerviews.AllegeRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.LabResultRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.MyDoctorRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.NotesRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.PrescriptionRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CareFragment extends Fragment {

    public String patientID;

    public CareFragment() {

    }

    public static CareFragment newInstance(String patientID,
                                           String param2) {
        CareFragment fragment = new CareFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_care, container,
                false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        List<UserData> userData =
                userDatabase.userDataDAO().getAllUserData();


        RecyclerView recyclerView =
                view.findViewById(R.id.recviewPatientInfor);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        String url = getString(R.string.endpoint);


        Button btnMedicine, btnNotes, btnPrescription, btnDoctors,
                btnHistory, btnLabResults;

        view.findViewById(R.id.btnPatientNotes).setOnClickListener(l -> {
            // show patient notes on recyclerview

            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("category", "notes");
                params.put("value", "0");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "patient/view",
                    params,
                    response -> {
                        try {
                            JSONArray notes = response.getJSONArray(
                                    "notes");
                            ArrayList<Note> noteArrayList =
                                    new ArrayList<>();

                            System.out.println("Notes from server : " + notes);

                            for (int num = 0; num < notes.length(); num++) {
                                JSONObject note =
                                        notes.getJSONObject(num);
                                String author = note.getString(
                                        "doctor");

                                String date = note.getString("date");

                                String content = note.getString(
                                        "content");
                                noteArrayList.add(new Note(date,
                                        content, author));
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            NotesRecyclerviewAdapter notesRecyclerviewAdapter = new NotesRecyclerviewAdapter();
                            notesRecyclerviewAdapter.setNotes(noteArrayList);
                            recyclerView.setAdapter(notesRecyclerviewAdapter);

                        } catch (JSONException e) {
                            try {
                                System.out.println("Error from server: " + response.get("error"));
                            } catch (JSONException ex) {
                                System.out.println("Error in client: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        System.out.println("Error from server and " +
                                "client: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);
        });


        view.findViewById(R.id.btnPatientMedicine).setOnClickListener(l -> {
            // show patient medicine
        });

        view.findViewById(R.id.btnPatientDiseases).setOnClickListener(l -> {
            // show patient diseases
        });

        view.findViewById(R.id.btnPatientHistory).setOnClickListener(l -> {
            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("category", "alleges");
                params.put("value", "0");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "patient/view",
                    params,
                    response -> {
                        try {
                            JSONArray alleges_ = response.getJSONArray(
                                    "alleges");

                            ArrayList<Allege> alleges =
                                    new ArrayList<>();

                            System.out.println("Alleges from server : "
                                    + alleges_);

                            for (int num = 0; num < alleges_.length(); num++) {
                                JSONObject prescription =
                                        alleges_.getJSONObject(num);

                                String allege = prescription.getString(
                                        "allege");

                                String note = prescription.getString(
                                        "note");

                                String reaction =
                                        prescription.getString(
                                                "reaction");

                                String date = prescription.getString(
                                        "date");
                                alleges.add(new Allege(allege, note,
                                        date, reaction));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            AllegeRecyclerviewAdapter recyclerviewAdapter = new AllegeRecyclerviewAdapter();
                            recyclerviewAdapter.setAlleges(alleges);
                            recyclerView.setAdapter(recyclerviewAdapter);

                        } catch (JSONException e) {
                            try {
                                System.out.println("Error from server: " + response.get("error"));
                            } catch (JSONException ex) {
                                System.out.println("Error in client: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        System.out.println("Error from server and " +
                                "client: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);
        });

        view.findViewById(R.id.btnPatientDoctors).setOnClickListener(l -> {
            // show patient doctors

            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter =
                    new MyDoctorRecyclerviewAdapter();

            myDoctorRecyclerviewAdapter.frag = "care";

            ArrayList<MyDoctorItem> myDoctorItems = new ArrayList<>();


            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("category", "doctors");
                params.put("value", "0");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "patient/view",
                    params,
                    response -> {
                        try {
                            response.get("doctors");
                            JSONArray people = response.getJSONArray(
                                    "doctors");
                            System.out.println("My Doctors : " + people);
                            for (int num = 0; num < people.length(); num++) {
                                JSONObject person =
                                        people.getJSONObject(num);
                                String name = person.getString("name");
                                String hospital = person.getString(
                                        "hospital");
                                String work = person.getString("work");
                                String username = person.getString(
                                        "username");
                                String approver = null;
                                boolean approved = false;
                                boolean requested = false;

                                myDoctorItems.add(new MyDoctorItem
                                        (name, "", "", work, hospital,
                                                username, approver,
                                                requested, approved));
                            }
                            myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                            System.out.println("MY doctor items : " + myDoctorItems);
                            recyclerView.setAdapter(myDoctorRecyclerviewAdapter);

                        } catch (JSONException e) {
                            try {
                                System.out.println("Error from server: " + response.get("error"));
                            } catch (JSONException ex) {
                                System.out.println("Error in client: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        System.out.println("Error from server and " +
                                "client: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);

        });

        view.findViewById(R.id.btnPatientMedicine).setOnClickListener(l -> {
            // show patient prescriptions
            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("category", "medicine");
                params.put("value", "0");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "patient/view",
                    params,
                    response -> {
                        try {
                            JSONArray medicine = response.getJSONArray(
                                    "medicine");
                            ArrayList<Prescription> prescriptions =
                                    new ArrayList<>();

                            System.out.println("Notes from server : " + medicine);

                            for (int num = 0; num < medicine.length(); num++) {
                                JSONObject prescription =
                                        medicine.getJSONObject(num);

                                String drug = prescription.getString(
                                        "medicine");

                                String qty = prescription.getString("qty");

                                String patient = prescription.getString(
                                        "patient");

                                String note = prescription.getString(
                                        "note");

                                String doctor = prescription.getString(
                                        "doctor");
                                String date = prescription.getString(
                                        "date");
                                prescriptions.add(new Prescription(drug,
                                        note, date, qty, doctor));

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            PrescriptionRecyclerviewAdapter recyclerviewAdapter = new PrescriptionRecyclerviewAdapter();
                            recyclerviewAdapter.setPrescriptions(prescriptions);
                            recyclerView.setAdapter(recyclerviewAdapter);


                        } catch (JSONException e) {
                            try {
                                System.out.println("Error from server: " + response.get("error"));
                            } catch (JSONException ex) {
                                System.out.println("Error in client: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        System.out.println("Error from server and " +
                                "client: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);

        });

        view.findViewById(R.id.btnPatientLabResults).setOnClickListener(l -> {
            // show patient lab results
            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("category", "results");
                params.put("value", "0");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "patient/view",
                    params,
                    response -> {
                        try {
                            JSONArray results = response.getJSONArray(
                                    "results");
                            ArrayList<LabResult> labResults =
                                    new ArrayList<>();

                            for (int num = 0; num < results.length(); num++) {
                                JSONObject result =
                                        results.getJSONObject(num);

                                String testName = result.getString(
                                        "test");

                                String testCode = result.getString(
                                        "test_code");

                                String resultName = result.getString(
                                        "result");

                                String resultCode = result.getString(
                                        "result_code");
                                String testDate = result.getString(
                                        "date");

                                labResults.add(new LabResult(testName,
                                        testCode, resultName, resultCode
                                        , testDate));
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            LabResultRecyclerviewAdapter labResultRecyclerviewAdapter =
                                    new LabResultRecyclerviewAdapter();
                            labResultRecyclerviewAdapter.setLabResults(labResults);
                            recyclerView.setAdapter(labResultRecyclerviewAdapter);

                        } catch (JSONException e) {
                            try {
                                System.out.println("Error from server: " + response.get("error"));
                            } catch (JSONException ex) {
                                System.out.println("Error in client: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        System.out.println("Error from server and " +
                                "client: " + error.getMessage());
                    });

            requestQueue.add(jsonObjectRequest);
        });
        // recyclerview for displaying all the patient care information
        // based on category selected

        return view;
    }
}