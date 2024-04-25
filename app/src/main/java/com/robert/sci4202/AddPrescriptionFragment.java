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

public class AddPrescriptionFragment extends Fragment {
    public String patient;
    public String docName;


    public AddPrescriptionFragment() {
        // Required empty public constructor
    }

    public static AddPrescriptionFragment newInstance(String param1,
                                                      String param2) {
        AddPrescriptionFragment fragment = new AddPrescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_prescription,
                container, false);
        UserDatabase userDatabase = UserDatabase
                .getINSTANCE(view.getContext());

        System.out.println("Infor from fragment : " + patient + " -> " + docName);

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        EditText etPrescroptionNote =
                view.findViewById(R.id.etPrescriptionNote);
        EditText etPrescriptionMedicine =
                view.findViewById(R.id.etMedicineName);
        EditText etPrescriptionQty = view.findViewById(R.id.etMedicineQty);


        view.findViewById(R.id.btnSavePrescription).setOnClickListener(l -> {


            if (etPrescriptionMedicine.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Medicine must be more" +
                        " that 10 characters", Toast.LENGTH_SHORT).show();
            } else if (etPrescriptionQty.getText().toString().isBlank()) {
                Toast.makeText(view.getContext(), "Qty must be more that" +
                        " 10 characters", Toast.LENGTH_SHORT).show();
            } else {

                new Thread(() -> {

                    Map<String, Object> params = new HashMap<>();
                    try {
                        params.put("type", "prescription");
                        params.put("patient", patient);
                        params.put("doctor", userData.userID);
                        params.put("author", userData.fullName);
                        params.put("medicine_name",
                                etPrescriptionMedicine.getText().toString());
                        params.put("qty",
                                etPrescriptionQty.getText().toString());
                        params.put("note",
                                etPrescroptionNote.getText().toString());
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
                                fragment.category = "prescription";
                                fragment.patient = patient;
                                System.out.println("medicine " +
                                        "clicked");
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