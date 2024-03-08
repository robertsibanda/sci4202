package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.MyDoctorItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDoctorRecyclerviewAdapter
        extends RecyclerView.Adapter<MyDoctorRecyclerviewAdapter.ViewHolder>{

    private ArrayList<MyDoctorItem> myDoctorItems = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.barDocRating.setRating(4.5f);
        holder.txtDocName.setText(myDoctorItems.get(position).getName());
        holder.txtDocHosp.setText(myDoctorItems.get(position).getName());
        holder.txtDocPractice.setText(myDoctorItems.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return myDoctorItems.size();
    }

    public void setMyDoctorItems(ArrayList<MyDoctorItem> myDoctorItems) {
        this.myDoctorItems = myDoctorItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDocName, txtDocContact, txtDocHosp, txtDocPractice;
        RatingBar barDocRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocName = itemView.findViewById(R.id.txtDocName);
            txtDocContact =itemView.findViewById(R.id.txtDocContact);
            txtDocHosp = itemView.findViewById(R.id.txtDocHospital);
            txtDocPractice = itemView.findViewById(R.id.txtDocPractice);
            barDocRating = itemView.findViewById(R.id.barRatingDocRating);
        }
    }
}
