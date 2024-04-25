package com.robert.sci4202.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.robert.sci4202.DoctorPatientCareFragment;
import com.robert.sci4202.R;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.Patient;

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

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(holder.btnAddPatient.getContext());

        if (patients.get(position).getCanView() | patients.get(position).getCanUpdate()) {
            holder.btnAddPatient.setText("View");

        } else {
            holder.btnAddPatient.setText("Request");
        }


        holder.btnAddPatient.setOnClickListener(l -> {
            if (holder.btnAddPatient.getText() == "Request") {
                // TODO make a request to the patient to view their
                //  information

            } else if (holder.btnAddPatient.getText().equals("View")) {
                DoctorPatientCareFragment careFragment =
                        new DoctorPatientCareFragment();
                careFragment.fullname =
                        patients.get(position).getFullName();
                careFragment.contact = patients.get(position).getContact();
                careFragment.patientID =
                        patients.get(position).getUsername();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment,
                                careFragment)
                        .commit();
            }
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
        Button btnAddPatient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPatientName = itemView.findViewById(R.id.txtPatientName);
            txtPatientContact =
                    itemView.findViewById(R.id.txtPatientContact);
            btnAddPatient = itemView.findViewById(R.id.btnAddPatient);

        }
    }
}
