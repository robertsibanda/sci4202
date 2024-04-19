package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab_result_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTestResultCode.setText(labResults.get(position).getResultCode());
        holder.txtTestCode.setText(labResults.get(position).getTestCode());
        holder.txtTestName.setText(labResults.get(position).getTestName());
        holder.txtTestResult.setText(labResults.get(position).getResultName());
        holder.txtTestDate.setText(labResults.get(position).getTestDate());
    }

    @Override
    public int getItemCount() {
        return labResults.size();
    }

    public void setLabResults(ArrayList<LabResult> labResults) {
        this.labResults = labResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTestDate, txtTestName, txtTestCode, txtTestResult,
                txtTestResultCode;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTestDate = itemView.findViewById(R.id.txtTestDate);
            txtTestName = itemView.findViewById(R.id.txtTestName);
            txtTestCode = itemView.findViewById(R.id.txtTestCode);
            txtTestResult = itemView.findViewById(R.id.txtTestResult);
            txtTestResultCode = itemView.findViewById(R.id.txtResultCode);
        }
    }
}
