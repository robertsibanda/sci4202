package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);


        RecyclerView recyclerView =
                view.findViewById(R.id.recviewPatientInfor);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        String url = getString(R.string.endpoint);


        Button btnMedicine, btnNotes, btnPrescription, btnDoctors,
                btnHistory, btnLabResults;

        view.findViewById(R.id.btnPatientNotes).setOnClickListener(l -> {
            // show patient notes on recyclerview

            System.out.println("Cliked Notes");

            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            NotesRecyclerviewAdapter notesRecyclerviewAdapter =
                    new NotesRecyclerviewAdapter();

            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("record", "notes");
                params.put("patient", userData.userID);

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "view_health_records",
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
                    JSONArray notes =
                            result.getResult().getJSONArray("success");
                    ArrayList<Note> noteArrayList =
                            new ArrayList<>();
                    for (int num = 0; num < notes.length(); num++) {
                        JSONObject note =
                                notes.getJSONObject(num);

                        String author = "Dr" + note.getString(
                                "author");

                        String date = note.getString("date");

                        String content = note.getString(
                                "content");
                        noteArrayList.add(new Note(date,
                                content, author));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            notesRecyclerviewAdapter.setNotes(noteArrayList);
                            recyclerView.setAdapter(notesRecyclerviewAdapter);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        });

        view.findViewById(R.id.btnPatientDiseases).setOnClickListener(l -> {
            // show patient diseases
        });

        view.findViewById(R.id.btnPatientAlleges).setOnClickListener(l -> {
            // show patient alleges
            System.out.println("Cliked alleges");
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            AllegeRecyclerviewAdapter allegeRecyclerviewAdapter =
                    new AllegeRecyclerviewAdapter();

            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("record", "allege");
                params.put("patient", userData.userID);

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "view_health_records",
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
                    JSONArray alleges =
                            result.getResult().getJSONArray("success");

                    ArrayList<Allege> allegeArrayList =
                            new ArrayList<>();

                    for (int num = 0; num < alleges.length(); num++) {
                        JSONObject allegesJSONObject =
                                alleges.getJSONObject(num);

                        String allegeName =
                                allegesJSONObject.getString(
                                        "allege");

                        String allegeNote =
                                allegesJSONObject.getString(
                                        "note");

                        String allegeReaction =
                                allegesJSONObject.getString(
                                        "reaction");
                        String date = allegesJSONObject.getString(
                                "date");


                        allegeArrayList.add(new Allege(allegeName,
                                allegeNote, date, allegeReaction));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            allegeRecyclerviewAdapter.setAlleges(allegeArrayList);
                            recyclerView.setAdapter(allegeRecyclerviewAdapter);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        });

        view.findViewById(R.id.btnPatientDoctors).setOnClickListener(l -> {
            // show patient doctors

            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("userid", userData.userID);

                MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter =
                        new MyDoctorRecyclerviewAdapter();
                myDoctorRecyclerviewAdapter.fragmentManager =
                        getParentFragmentManager();

                myDoctorRecyclerviewAdapter.frag = "care";

                ArrayList<MyDoctorItem> myDoctorItems = new ArrayList<>();

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "get_my_doctors",
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
                    JSONArray people =
                            result.getResult().getJSONArray("success");
                    for (int num = 0; num < people.length(); num++) {
                        JSONObject person =
                                people.getJSONObject(num);
                        String fullname = person.getString(
                                "fullname");
                        String contact = person.getString(
                                "contact");

                        String organistion = person.getString(
                                "organisation");
                        boolean canView = person.getBoolean(
                                "view");
                        boolean canUpdate = person.getBoolean(
                                "update");

                        String occupation = person.getString(
                                "occupation");

                        String userid = person.getString("userid");

                        String biography = person.getString("bio");

                        myDoctorItems.add(new MyDoctorItem(fullname, "",
                                contact, occupation, organistion, userid
                                , canView, canUpdate, biography));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                            recyclerView.setAdapter(myDoctorRecyclerviewAdapter);
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();


        });

        view.findViewById(R.id.btnPatientMedicine).setOnClickListener(l -> {
            // show patient prescriptions
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            PrescriptionRecyclerviewAdapter prescriptionRecyclerviewAdapter =
                    new PrescriptionRecyclerviewAdapter();

            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("record", "prescription");
                params.put("patient", userData.userID);

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "view_health_records",
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
                    JSONArray prescriptions =
                            result.getResult().getJSONArray("success");
                    System.out.println("PRescriptions : " + prescriptions);

                    ArrayList<Prescription> prescriptionArrayList =
                            new ArrayList<>();

                    for (int num = 0; num < prescriptions.length(); num++) {
                        JSONObject prescriptionsJSONObject =
                                prescriptions.getJSONObject(num);

                        String medicine =
                                prescriptionsJSONObject.getString(
                                        "medicine_name");

                        String qty =
                                prescriptionsJSONObject.getString(
                                        "qty");

                        String note =
                                prescriptionsJSONObject.getString(
                                        "note");

                        String date =
                                prescriptionsJSONObject.getString(
                                        "date");

                        String doctor =
                                prescriptionsJSONObject.getString(
                                        "doctor");
                        String author =
                                "Dr" + prescriptionsJSONObject.getString(
                                        "author");


                        prescriptionArrayList.add(new Prescription(medicine, note, date, qty, author));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            prescriptionRecyclerviewAdapter.setPrescriptions(prescriptionArrayList);
                            recyclerView.setAdapter(prescriptionRecyclerviewAdapter);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        });

        view.findViewById(R.id.btnPatientLabResults).setOnClickListener(l -> {
            // show patient lab results
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            LabResultRecyclerviewAdapter labResultRecyclerviewAdapter =
                    new LabResultRecyclerviewAdapter();

            new Thread(() -> {

                Map<String, String> params = new HashMap<>();

                params.put("record", "test");
                params.put("patient", userData.userID);

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "view_health_records",
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
                    JSONArray results =
                            result.getResult().getJSONArray("success");
                    System.out.println("Results : " + results);
                    ArrayList<LabResult> labResults =
                            new ArrayList<>();

                    for (int num = 0; num < results.length(); num++) {
                        JSONObject labResult =
                                results.getJSONObject(num);

                        String testName = labResult.getString(
                                "test");

                        String testCode = labResult.getString(
                                "test_code");

                        String resultName = labResult.getString(
                                "result");
                        String resultCode = labResult.getString(
                                "result_code");

                        String testDate = labResult.getString("date");

                        labResults.add(new LabResult(testName,
                                testCode, resultName, resultCode,
                                testDate));
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Do something on UiThread
                            labResultRecyclerviewAdapter.setLabResults(labResults);
                            recyclerView.setAdapter(labResultRecyclerviewAdapter);
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        });
        // recyclerview for displaying all the patient care information
        // based on category selected

        return view;
    }
}