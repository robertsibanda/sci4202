package com.robert.sci4202.recyclerviews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.R;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.UserNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserNotificationRecyclerViewAdapter
        extends RecyclerView.Adapter<UserNotificationRecyclerViewAdapter.ViewHolder> {

    private ArrayList<UserNotification> notifications;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                         int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_list_item, viewGroup,
                        false);

        ViewHolder viewHolder = new ViewHolder(view);
        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());

        Button btnAccept, btnReject;
        btnAccept = view.findViewById(R.id.btnAccept);
        btnReject = view.findViewById(R.id.btnReject);


        String urlEnd = null;

        if (Objects.equals(userDatabase.userDataDAO().getAllUserData().get(0).userType, "doctor")) {
            urlEnd = "doctor/approve";
        } else {
            urlEnd = "patient/approve";
        }

        String finalUrlEnd = urlEnd;

        view.findViewById(R.id.btnSetNotificationRead).setOnClickListener(l -> {
            //mark notification as read
            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());

            String url =
                    view.getContext().getString(R.string.endpoint) +
                            "notification/read";

            JSONObject params = new JSONObject();

            try {
                params.put("notification", viewHolder.id);

                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    params,
                    response -> {
                        try {
                            response.get("success");
                            System.out.println("Response from server : " + response);
                            this.notifications.remove(viewHolder.index);
                        } catch (JSONException e) {
                            try {
                                System.out.println("Error: " + e.getMessage());
                                Toast.makeText(view.getContext(),
                                        response.get("error").toString()
                                        , Toast.LENGTH_SHORT).show();
                            } catch (JSONException ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        Toast.makeText(view.getContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

            );

            requestQueue.add(jsonObjectRequest);
        });

        btnReject.setOnClickListener(l -> {

            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());

            String url =
                    view.getContext().getString(R.string.endpoint) + finalUrlEnd;

            JSONObject params = new JSONObject();

            try {
                params.put("notification", viewHolder.id);
                params.put("other", viewHolder.other);
                params.put("approval", "0");
                params.put("category", "relation");

                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    params,
                    response -> {
                        try {
                            response.get("success");
                            System.out.println("Response from server : " + response);
                            this.notifications.remove(viewHolder.index);
                        } catch (JSONException e) {
                            try {
                                System.out.println("Error: " + e.getMessage());
                                Toast.makeText(view.getContext(),
                                        response.get("error").toString()
                                        , Toast.LENGTH_SHORT).show();
                            } catch (JSONException ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        Toast.makeText(view.getContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

            );

            requestQueue.add(jsonObjectRequest);
        });

        btnAccept.setOnClickListener(l -> {
            Toast.makeText(view.getContext(),
                    "Accept clicked " + viewHolder, Toast.LENGTH_SHORT).show();
            RequestQueue requestQueue =
                    Volley.newRequestQueue(view.getContext());

            String url =
                    view.getContext().getString(R.string.endpoint) + finalUrlEnd;

            JSONObject params = new JSONObject();

            try {
                params.put("notification", viewHolder.id);
                params.put("other", viewHolder.other);
                params.put("approval", "1");
                params.put("category", "relation");

                params.put("authorization",
                        "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    params,
                    response -> {
                        try {
                            response.get("success");
                            System.out.println("Response from server : " + response);
                            this.notifications.remove(viewHolder.index);
                            Toast.makeText(view.getContext(), "User " +
                                    "added successfully",
                                    Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            try {
                                System.out.println("Error: " + e.getMessage());
                                Toast.makeText(view.getContext(),
                                        response.get("error").toString()
                                        , Toast.LENGTH_SHORT).show();
                            } catch (JSONException ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }
                    },
                    error -> {
                        Toast.makeText(view.getContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

            );

            requestQueue.add(jsonObjectRequest);
        });


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,
                                 @SuppressLint("RecyclerView") int i) {
        viewHolder.txtNotificationContent.setText(notifications.get(i).getNotificationContent());
        viewHolder.txtNofificationTitle.setText(notifications.get(i).getNotificationTitle());
        viewHolder.id = notifications.get(i).getNotificationID();
        viewHolder.other = notifications.get(i).getOther_();
        viewHolder.txtNotificationId.setText(viewHolder.id);
        viewHolder.notificationType_ =
                notifications.get(i).getNotificationType();

        if (viewHolder.notificationType_.equals("relation") || viewHolder.notificationType_.equals("appointment")) {
            viewHolder.itemView.findViewById(R.id.btnAccept).setVisibility(View.VISIBLE);
            viewHolder.itemView.findViewById(R.id.btnReject).setVisibility(View.VISIBLE);
        } else {
            viewHolder.itemView.findViewById(R.id.btnAccept).setVisibility(View.INVISIBLE);
            viewHolder.itemView.findViewById(R.id.btnReject).setVisibility(View.INVISIBLE);
            viewHolder.itemView.findViewById(R.id.btnAccept).setVisibility(View.GONE);
            viewHolder.itemView.findViewById(R.id.btnReject).setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotifications(ArrayList<UserNotification> notifications) {
        this.notifications = notifications;
    }

    public void deleteNotification(String id) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNofificationTitle, txtNotificationContent,
                txtNotificationId;
        String id;

        int index;
        String other, notificationType_;
        Button btnReject, btnAccept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNofificationTitle =
                    itemView.findViewById(R.id.txtNofificationTitle);
            txtNotificationContent =
                    itemView.findViewById(R.id.txtNotificationContent);
            txtNotificationId =
                    itemView.findViewById(R.id.txtNotificationId);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);

        }
    }
}
