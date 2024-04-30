package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.robert.sci4202.R;
import com.robert.sci4202.objects.Specialty;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpecialtyRecyclerviewAdapter extends RecyclerView.Adapter<SpecialtyRecyclerviewAdapter.ViewHolder> {

    private ArrayList<Specialty> specialtyArrayList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialty_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.btnSpecialty.setText(specialtyArrayList.get(position).getSpecialty());
    }

    @Override
    public int getItemCount() {
        return specialtyArrayList.size();
    }

    public void setSpecialtyArrayList(ArrayList<Specialty> specialtyArrayList) {
        this.specialtyArrayList = specialtyArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ExtendedFloatingActionButton btnSpecialty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSpecialty = itemView.findViewById(R.id.btnSpecialty);
        }
    }
}
