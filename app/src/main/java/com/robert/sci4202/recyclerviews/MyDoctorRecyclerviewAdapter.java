package com.robert.sci4202.recyclerviews;

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
import com.robert.sci4202.objects.MyDoctorItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDoctorRecyclerviewAdapter
        extends RecyclerView.Adapter<MyDoctorRecyclerviewAdapter.ViewHolder> {

    public boolean buttons = true;
    private ArrayList<MyDoctorItem> myDoctorItems = new ArrayList<>();
    public String url = null;


    public String frag;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(holder.btnAddDoc.getContext());
        holder.txtDocName.setText(myDoctorItems.get(position).getName());
        holder.txtDocHosp.setText(myDoctorItems.get(position).getHospital());
        holder.txtDocPractice.setText(myDoctorItems.get(position).getProfession());
        holder.approver = myDoctorItems.get(position).getAprover();

        if (buttons == false) {
            holder.btnAddDoc.setVisibility(View.INVISIBLE);
            holder.btnAddDoc.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.INVISIBLE);
            holder.btnReject.setVisibility(View.GONE);
        }

        System.out.println("Fragment  : " + frag);

        if (Objects.equals(frag, "care")) {
            holder.btnAddDoc.setVisibility(View.INVISIBLE);
            holder.btnAddDoc.setVisibility(View.GONE);
        } else if (myDoctorItems.get(position).isApproved()) {
            holder.btnAddDoc.setText("View");
            holder.btnAddDoc.setVisibility(View.INVISIBLE);
            holder.btnAddDoc.setVisibility(View.GONE);
        } else if (myDoctorItems.get(position).isRequested() & !myDoctorItems.get(position).isApproved()) {


            if (Objects.equals(userDatabase.userDataDAO().getAllUserData().get(0).userName, holder.approver)) {
                holder.btnAddDoc.setText("Approve");
            } else {
                holder.btnAddDoc.setText("Pending");

                holder.btnAddDoc.setClickable(false);
            }
        } else {
            holder.btnAddDoc.setOnClickListener(l -> {


                RequestQueue requestQueue =
                        Volley.newRequestQueue(holder.btnAddDoc.getContext());
                JSONObject params = new JSONObject();
                try {
                    params.put("authorization",
                            "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                    params.put("doctor",
                            myDoctorItems.get(position).getUsername());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url + "patient/add-doc",
                                params,
                                response -> {
                                    try {
                                        response.get("success");
                                        Toast.makeText(holder.btnAddDoc.getContext(), "Request submitted", Toast.LENGTH_SHORT).show();
                                        holder.btnAddDoc.setText(
                                                "Pending");
                                    } catch (JSONException e) {
                                        try {
                                            Toast.makeText(holder.btnAddDoc.getContext(), response.get("error").toString(),
                                                    Toast.LENGTH_SHORT).show();
                                            System.out.println("Error " +
                                                    "from " +
                                                    "server: " + response.get(
                                                    "error"));
                                        } catch (JSONException ex) {
                                            System.out.println("Error in" +
                                                    " client:" +
                                                    " " + ex.getMessage());
                                        }
                                    }
                                },
                                error -> {
                                    System.out.println("Error from " +
                                            "server and " +
                                            "client: " + error.getMessage());
                                });

                requestQueue.add(jsonObjectRequest);
            });
        }

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
        Button btnAddDoc, btnReject;
        String approver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocName = itemView.findViewById(R.id.txtDocName);
            txtDocContact = itemView.findViewById(R.id.txtDocContact);
            txtDocHosp = itemView.findViewById(R.id.txtDocHospital);
            txtDocPractice = itemView.findViewById(R.id.txtDocPractice);
            btnAddDoc = itemView.findViewById(R.id.btnAddDoc);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
