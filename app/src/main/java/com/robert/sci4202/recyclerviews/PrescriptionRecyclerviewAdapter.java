package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.Prescription;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrescriptionRecyclerviewAdapter
        extends RecyclerView.Adapter<PrescriptionRecyclerviewAdapter.ViewHolder>{

    private ArrayList<Prescription> prescriptions = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prescription_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNote.setText(prescriptions.get(position).getNote());
        holder.txtQty.setText(prescriptions.get(position).getQuantity());
        holder.txtMedicine.setText(prescriptions.get(position).getMedication());
        holder.txtDate.setText(prescriptions.get(position).getDate());
        holder.txtDoc.setText(prescriptions.get(position).getDoctor());
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMedicine, txtQty, txtNote, txtDate, txtDoc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedicine = itemView.findViewById(R.id.txtPrescriptionMedicine);
            txtQty = itemView.findViewById(R.id.txtPrescriptionQty);
            txtNote = itemView.findViewById(R.id.txtPrescriptionNote);
            txtDate = itemView.findViewById(R.id.txtPrescriptionDate);
            txtDoc = itemView.findViewById(R.id.txtPrescriptionDoc);
        }
    }
}
