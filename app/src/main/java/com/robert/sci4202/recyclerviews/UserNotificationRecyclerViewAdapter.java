package com.robert.sci4202.recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.UserNotification;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserNotificationRecyclerViewAdapter
        extends RecyclerView.Adapter <UserNotificationRecyclerViewAdapter.ViewHolder> {

    private ArrayList<UserNotification> notifications;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtNotificationText.setText(notifications.get(i).getNotificationText());
        viewHolder.txtNotificationType.setText(notifications.get(i).getNotificationType());
        viewHolder.id = notifications.get(i).getNotificationID();
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotifications(ArrayList<UserNotification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    public void deleteNotification(String id){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNotificationType, txtNotificationText;
        String id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotificationType = itemView.findViewById(R.id.txtNotificationType);
            txtNotificationText = itemView.findViewById(R.id.txtNotificationText);
        }
    }
}
