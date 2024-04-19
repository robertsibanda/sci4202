package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.CalenderItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalenderRecyclerviewAdapter extends RecyclerView.Adapter<CalenderRecyclerviewAdapter.ViewHolder> {

    private ArrayList<CalenderItem> calenderItems = new ArrayList<>();

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
        holder.eventTime.setText(calenderItems.get(position).getTime());
        holder.eventTitle.setText(calenderItems.get(position).getTitle());
        holder.txtOtherPerson.setText(calenderItems.get(position).getOther_person());
    }

    @Override
    public int getItemCount() {
        return calenderItems.size();
    }

    public void setCalenderItems(ArrayList<CalenderItem> calenderItems) {
        this.calenderItems = calenderItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle, eventTime, txtOtherPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.txtEventTitle);
            eventTime = itemView.findViewById(R.id.txtEventTime);
            txtOtherPerson = itemView.findViewById(R.id.txtOtherPerson);
        }
    }
}
