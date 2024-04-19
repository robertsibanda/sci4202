package com.robert.sci4202.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.DoctorPatientCareFragment;
import com.robert.sci4202.R;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.Patient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientRecyclerviewAdapter extends RecyclerView.Adapter<PatientRecyclerviewAdapter.ViewHolder> {

    private ArrayList<Patient> patients = new ArrayList<>();
    public String url = null;

    private Context context;

    private FragmentManager fragmentManager;


    public PatientRecyclerviewAdapter(Context context,
                                      FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.txtPatientName.setText(patients.get(position).getFullName());
        holder.txtPatientContact.setText(patients.get(position).getContact());
        holder.approver = patients.get(position).getApprover();
        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(holder.btnAddPatient.getContext());

        if (patients.get(position).getApproved()) {
            holder.btnAddPatient.setText("View");

        } else if (patients.get(position).getRequested() & !patients.get(position).getApproved()) {
            if (userDatabase.userDataDAO().getAllUserData().get(0).userName.equals(holder.approver)) {
                holder.btnAddPatient.setText("Approve");
            } else {
                holder.btnAddPatient.setText("Pending");
                holder.btnAddPatient.setClickable(false);
            }
        }


        holder.btnAddPatient.setOnClickListener(l -> {
            if (holder.btnAddPatient.getText() == "Approve") {
                RequestQueue requestQueue =
                        Volley.newRequestQueue(holder.btnAddPatient.getContext());
                JSONObject params = new JSONObject();
                try {
                    params.put("authorization",
                            "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                    params.put("other",
                            patients.get(position).getUsername());
                    params.put("category", "relation");
                    params.put("approval", "1");


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url + "doctor/approve",
                                params,
                                response -> {
                                    try {
                                        response.get("success");
                                        Toast.makeText(holder.btnAddPatient.getContext(), "Request submitted", Toast.LENGTH_SHORT).show();
                                        holder.btnAddPatient.setText(
                                                "View");
                                        holder.btnAddPatient.setClickable(false);
                                        holder.btnAddPatient.setClickable(false);
                                    } catch (JSONException e) {
                                        try {
                                            System.out.println("Error " +
                                                    "from " +
                                                    "server: " + response.get(
                                                    "error"));
                                        } catch (JSONException ex) {
                                            try {
                                                Toast.makeText(holder.btnAddPatient.getContext(), response.get("error").toString(),
                                                        Toast.LENGTH_SHORT).show();
                                            } catch (JSONException exc) {
                                                throw new RuntimeException(exc);
                                            }
                                            System.out.println("Error in" +
                                                    " client:" +
                                                    " " + ex.getMessage());
                                        }
                                    }
                                },
                                error -> {
                                    System.out.println("Error from " +
                                            "server and " +
                                            "client: " + error.getMessage());
                                });

                requestQueue.add(jsonObjectRequest);
            } else if (holder.btnAddPatient.getText().equals("View")) {
                System.out.println("Viewing Patient");
                DoctorPatientCareFragment careFragment =
                        new DoctorPatientCareFragment();
                careFragment.patientID =
                        patients.get(position).getUsername();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment,
                                careFragment)
                        .commit();

            } else if (holder.btnAddPatient.getText().toString().contains("Add")) {
                RequestQueue requestQueue =
                        Volley.newRequestQueue(holder.btnAddPatient.getContext());
                JSONObject params = new JSONObject();
                try {
                    params.put("authorization",
                            "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                    params.put("patient",
                            patients.get(position).getUsername());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url + "doctor/add-patient",
                                params,
                                response -> {
                                    try {
                                        response.get("success");
                                        Toast.makeText(holder.btnAddPatient.getContext(), "Request submitted", Toast.LENGTH_SHORT).show();
                                        holder.btnAddPatient.setText(
                                                "Pending");
                                        holder.btnAddPatient.setClickable(false);
                                    } catch (JSONException e) {
                                        try {
                                            Toast.makeText(holder.btnAddPatient.getContext(), response.get("error").toString(),
                                                    Toast.LENGTH_SHORT).show();
                                            System.out.println("Error " +
                                                    "from " +
                                                    "server: " + response.get(
                                                    "error"));
                                        } catch (JSONException ex) {
                                            System.out.println("Error in" +
                                                    " client:" +
                                                    " " + ex.getMessage());
                                        }
                                    }
                                },
                                error -> {
                                    System.out.println("Error from " +
                                            "server and " +
                                            "client: " + error.getMessage());
                                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        holder.btnReject.setOnClickListener(l -> {
            RequestQueue requestQueue =
                    Volley.newRequestQueue(holder.btnAddPatient.getContext());
            JSONObject params = new JSONObject();
            try {
                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                params.put("other", patients.get(position).getUsername());
                params.put("category", "relation");
                params.put("approval", "-1");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "doctor/approve",
                    params,
                    response -> {
                        try {
                            response.get("success");
                            Toast.makeText(holder.btnAddPatient.getContext(), "Request submitted", Toast.LENGTH_SHORT).show();
                            holder.btnAddPatient.setText("Add Patient");
                            holder.btnAddPatient.setClickable(false);
                            holder.btnReject.setClickable(false);
                        } catch (JSONException e) {
                            try {
                                Toast.makeText(holder.btnAddPatient.getContext(), response.get("error").toString(),
                                        Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPatientName, txtPatientContact;
        Button btnAddPatient, btnReject;

        String approver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtPatientContact =
                    itemView.findViewById(R.id.txtPatientContact);
            btnAddPatient = itemView.findViewById(R.id.btnAddPatient);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
