package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientInforFragment extends Fragment {

    public String category = null;

    public String patient = null;

    public PatientInforFragment() {
        // Required empty public constructor
    }

    public static PatientInforFragment newInstance() {

        return new PatientInforFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void ShowFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.viewPatientInforNav, fragment)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_infor,
                container,
                false);

        System.out.print("Category for patient information : " + category + " -> person : " + patient);


        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());


        String docName =
                userDatabase.userDataDAO().getAllUserData().get(0).userName;
        String accessToken =
                userDatabase.userDataDAO().getAllUserData().get(0).accessToken;

        Button btnAddPatientInfo =
                view.findViewById(R.id.btnAddPatientInfor);

        RecyclerView recyclerView =
                view.findViewById(R.id.recviewPatientInfor);


        switch (category) {
            case "doctors":
                btnAddPatientInfo.setVisibility(View.INVISIBLE);
                btnAddPatientInfo.setVisibility(View.GONE);
                break;
            case "disease":
                btnAddPatientInfo.setText("Add Diagnosiss");
                break;
            case "medicine":
                btnAddPatientInfo.setText("Add Medicine");
                break;
            case "notes":
                btnAddPatientInfo.setText("Add Note");
                break;
            case "prescription":
                btnAddPatientInfo.setText("Add Prescription");
                break;
            case "results":
                btnAddPatientInfo.setText("Add Result");
                break;
            case "alleges":
                btnAddPatientInfo.setText("Add Allege");
                break;
        }


        btnAddPatientInfo.setOnClickListener(l -> {

            if (btnAddPatientInfo.getText() == "Add Note") {
                AddNotesFragment fragment = new AddNotesFragment();
                fragment.patient = this.patient;
                ShowFragment(fragment);
            } else if (btnAddPatientInfo.getText().equals("Add Medicine")) {
                AddPrescriptionFragment fragment =
                        new AddPrescriptionFragment();
                fragment.patient = this.patient;
                ShowFragment(fragment);
            } else if (btnAddPatientInfo.getText().equals("Add Result")) {
                AddResultsFragment fragment = new AddResultsFragment();
                fragment.patient = this.patient;
                ShowFragment(fragment);
            } else if (btnAddPatientInfo.getText().equals("Add Allege")) {
                AddAllegeFragment fragment = new AddAllegeFragment();
                fragment.patient = this.patient;
                ShowFragment(fragment);
            }
        });


        RequestQueue requestQueue =
                Volley.newRequestQueue(view.getContext());

        String url = getString(R.string.endpoint) + "doctor/view";

        JSONObject params = new JSONObject();
        try {
            params.put("category", category);
            params.put("person", patient);
            params.put("authorization",
                    "Bearer " + userDatabase.userDataDAO()
                            .getAllUserData().get(0).accessToken);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                response -> {

                    System.out.println("Response from server : " + response);
                    try {
                        switch (category) {
                            case "notes": {
                                JSONArray notes = response.getJSONArray(
                                        "notes");
                                ArrayList<Note> noteArrayList =
                                        new ArrayList<>();

                                System.out.println("Notes from server : "
                                        + notes);

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
                                break;
                            }
                            case "results": {
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

                                    labResults.add(new LabResult(testName, testCode, resultName, resultCode, testDate));
                                }

                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                LabResultRecyclerviewAdapter labResultRecyclerviewAdapter =
                                        new LabResultRecyclerviewAdapter();
                                labResultRecyclerviewAdapter.setLabResults(labResults);
                                recyclerView.setAdapter(labResultRecyclerviewAdapter);
                                break;
                            }
                            case "disease": {
                                JSONArray illnesses =
                                        response.getJSONArray(
                                                "illnesses");
                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                                break;
                            }
                            case "medicine": {
                                JSONArray medicine = response.getJSONArray(
                                        "medicine");
                                ArrayList<Prescription> prescriptions =
                                        new ArrayList<>();

                                System.out.println("Notes from server : "
                                        + medicine);

                                for (int num = 0; num < medicine.length(); num++) {
                                    JSONObject prescription =
                                            medicine.getJSONObject(num);

                                    String drug = prescription.getString(
                                            "medicine");

                                    String qty = prescription.getString(
                                            "qty");

                                    String patient =
                                            prescription.getString(
                                                    "patient");

                                    String note = prescription.getString(
                                            "note");

                                    String doctor = prescription.getString(
                                            "doctor");
                                    String date = prescription.getString(
                                            "date");
                                    prescriptions.add(new Prescription(drug, note, date, qty, doctor));

                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                PrescriptionRecyclerviewAdapter recyclerviewAdapter = new PrescriptionRecyclerviewAdapter();
                                recyclerviewAdapter.setPrescriptions(prescriptions);
                                recyclerView.setAdapter(recyclerviewAdapter);

                                break;
                            }
                            case "alleges": {
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
                                break;
                            }

                            case "doctors": {
                                JSONArray doctors =
                                        response.getJSONArray("doctors");
                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                                System.out.println("Doctors : " + doctors);

                                ArrayList<MyDoctorItem> myDoctorItems =
                                        new ArrayList<>();

                                for (int num = 0; num < doctors.length(); num++) {
                                    JSONObject person =
                                            doctors.getJSONObject(num);
                                    String name = person.getString("name");
                                    String hospital = person.getString(
                                            "hospital");
                                    String work = person.getString("work");
                                    String username = person.getString(
                                            "username");
                                    String approver = person.getString(
                                            "approver");
                                    boolean approved =
                                            person.getBoolean("approved");
                                    boolean requested =
                                            person.getBoolean("requested");

                                    myDoctorItems.add(new MyDoctorItem
                                            (name, "", "", work,
                                                    hospital, username,
                                                    requested,
                                                    approved));
                                }

                                MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter =
                                        new MyDoctorRecyclerviewAdapter();

                                myDoctorRecyclerviewAdapter.url =
                                        getString(R.string.endpoint);
                                myDoctorRecyclerviewAdapter.buttons =
                                        false;
                                myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                                recyclerView.setAdapter(myDoctorRecyclerviewAdapter);

                                break;
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(view.getContext(), ex.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        try {
                            System.out.println("Error at patient " +
                                    "information: " + response.get(
                                    "error"));
                        } catch (Exception ex1) {
                            System.out.println("Error : " + ex.getMessage());
                            System.out.println("Error : " + ex1.getMessage());
                        }
                    }

                },

                error -> {

                });

        requestQueue.add(jsonObjectRequest);


        return view;
    }
}