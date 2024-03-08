package com.robert.sci4202.recyclerviews;

import android.view.View;
import android.view.ViewGroup;

import com.robert.sci4202.objects.Diagnosis;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiagnosisRecyclerviewAdapter
        extends RecyclerView.Adapter<DiagnosisRecyclerviewAdapter.ViewHolder>{

    private ArrayList<Diagnosis> diagnoses = new ArrayList<>();
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
        return diagnoses.size();
    }

    public void setDiagnoses(ArrayList<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
