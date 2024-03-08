package com.robert.sci4202.recyclerviews;

import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
