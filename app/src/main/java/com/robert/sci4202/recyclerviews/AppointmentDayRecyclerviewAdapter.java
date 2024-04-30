package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.AppointmentDay;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentDayRecyclerviewAdapter extends RecyclerView.Adapter<AppointmentDayRecyclerviewAdapter.ViewHolder> {

    private ArrayList<AppointmentDay> appointmentDays = new ArrayList<>();

    public ArrayList<View> createdViews = new ArrayList<>();

    public View parentView;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_list_item, parent, false);

        createdViews.add(view);

        view.setOnClickListener(l -> {
            View selected = parentView.findViewWithTag("selected");
            if (selected == null) {
                System.out.println("Selected is null");
                view.setTag("selected");
                view.setBackgroundColor(view.getResources().getColor(R.color.clicked_data));
                System.out.println("View selected : " + parentView.findViewWithTag("selected"));
            } else {
                selected.setBackgroundColor(view.getResources().getColor(R.color.grey));
                selected.setTag("unslected");
                view.setTag("selected");
                view.setBackgroundColor(view.getResources().getColor(R.color.clicked_data));
            }
        });

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.txtDay.setText(appointmentDays.get(position).getDay());
        holder.txtDate.setText(appointmentDays.get(position).getDate());
        holder.selected = false;

    }

    @Override
    public int getItemCount() {
        return appointmentDays.size();
    }

    public void setAppointmentDays(ArrayList<AppointmentDay> appointmentDays) {
        this.appointmentDays = appointmentDays;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDay, txtDate;
        boolean selected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
