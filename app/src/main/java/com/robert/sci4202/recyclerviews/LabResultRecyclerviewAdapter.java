package com.robert.sci4202.recyclerviews;

import android.view.View;
import android.view.ViewGroup;

import com.robert.sci4202.objects.LabResult;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabResultRecyclerviewAdapter
        extends RecyclerView.Adapter<LabResultRecyclerviewAdapter.ViewHolder>{


    private ArrayList<LabResult> labResults = new ArrayList<>();
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
        return labResults.size();
    }

    public void setLabResults(ArrayList<LabResult> labResults) {
        this.labResults = labResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
