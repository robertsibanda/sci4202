package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.robert.sci4202.R;
import com.robert.sci4202.objects.DoctorAppointment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAppointmentRecyclerviewAdapter extends RecyclerView.Adapter<DoctorAppointmentRecyclerviewAdapter.ViewHolder> {

    private ArrayList<DoctorAppointment> doctorAppointments =
            new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carlender_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        holder.txtDocfullName.setText(doctorAppointments.get(position).getDoctorName());
        holder.txtAppointmentDescription.setText(doctorAppointments.get(position).getAppointmendDescription());
        holder.txtDocProfession.setText(doctorAppointments.get(position).getDoctorProfession());
        holder.btnTime.setText(doctorAppointments.get(position).getGetAppointmentTime());
        holder.btnDate.setText(doctorAppointments.get(position).getAppointmentDate());

    }

    @Override
    public int getItemCount() {
        return doctorAppointments.size();
    }

    public void setDoctorAppointments(ArrayList<DoctorAppointment> doctorAppointments) {
        this.doctorAppointments = doctorAppointments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtDocfullName, txtDocProfession,
                txtAppointmentDescription;

        ExtendedFloatingActionButton btnDate, btnTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDocfullName = itemView.findViewById(R.id.txtDoctorName);
            txtDocProfession =
                    itemView.findViewById(R.id.txtDoctorProffession);
            txtAppointmentDescription =
                    itemView.findViewById(R.id.txtAppointmentDescription);

            btnDate = itemView.findViewById(R.id.btnAppointmentDate);
            btnTime = itemView.findViewById(R.id.btnAppointmentTime);
        }
    }
}
