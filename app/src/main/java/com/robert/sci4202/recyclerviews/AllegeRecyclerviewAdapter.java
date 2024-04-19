package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.Allege;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllegeRecyclerviewAdapter extends RecyclerView.Adapter<AllegeRecyclerviewAdapter.ViewHolder> {

    private ArrayList<Allege> alleges;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allege_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.txtAllegeReaction.setText(alleges.get(position).getReaction());
        holder.txtAllege.setText(alleges.get(position).getAllege());
        holder.txtAllegeNote.setText(alleges.get(position).getNote());
        holder.txtAllegeDate.setText(alleges.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return alleges.size();
    }

    public void setAlleges(ArrayList<Allege> alleges) {
        this.alleges = alleges;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAllege, txtAllegeNote, txtAllegeReaction,
                txtAllegeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAllege = itemView.findViewById(R.id.txtAllege);
            txtAllegeDate = itemView.findViewById(R.id.txtTestDate);
            txtAllegeNote = itemView.findViewById(R.id.txtAllegeNote);
            txtAllegeReaction =
                    itemView.findViewById(R.id.txtAllegeReaction);
        }
    }
}
