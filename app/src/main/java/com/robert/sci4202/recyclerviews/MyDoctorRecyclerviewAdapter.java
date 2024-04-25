package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.MyDoctorItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
                UserDatabase.getINSTANCE(holder.checkView.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        holder.txtDocName.setText(myDoctorItems.get(position).getName());
        holder.txtDocHosp.setText(myDoctorItems.get(position).getOrganisation());
        holder.txtDocPractice.setText(myDoctorItems.get(position).getProfession());
        holder.txtDocContact.setText(myDoctorItems.get(position).getContact());
        holder.checkView.setChecked(myDoctorItems.get(position).isCanView());
        holder.checkUpdate.setChecked(myDoctorItems.get(position).isCanUpdate());
        holder.doctor = myDoctorItems.get(position).getUserId();


        if (!Objects.equals(frag, "care")) {
            holder.checkUpdate.setEnabled(false);
            holder.checkView.setEnabled(false);
        }

        holder.checkUpdate.setOnClickListener(l -> {
            boolean update;
            if (holder.checkUpdate.isChecked()) {
                update = true;
            } else {
                update = false;
            }

            new Thread(() -> {
                Map<String, Object> params = new HashMap<>();
                try {
                    params.put("userid", userData.userID);
                    params.put("perm", "update");
                    params.put("perm-code", update);
                    params.put("doctor", holder.doctor);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "update_data_permissions",
                            params);
                    result.getResult().get("success");
                    System.out.println("Result from update :" + result.getResult());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }).start();
        });
        holder.checkView.setOnClickListener(l -> {

            boolean view;
            if (holder.checkView.isChecked()) {
                view = true;
            } else {
                view = false;
            }

            new Thread(() -> {
                Map<String, Object> params = new HashMap<>();
                try {
                    params.put("userid", userData.userID);
                    params.put("perm", "view");
                    params.put("perm-code", view);
                    params.put("doctor", holder.doctor);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    ServerResult result = RPCRequests.sendRequest(
                            "update_data_permissions",
                            params);
                    result.getResult().get("success");
                    System.out.println("Result from view :" + result.getResult());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }).start();

        });


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
        CheckBox checkView, checkUpdate;
        String doctor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDocName = itemView.findViewById(R.id.txtDocName);
            txtDocContact = itemView.findViewById(R.id.txtDocContact);
            txtDocHosp = itemView.findViewById(R.id.txtDocHospital);
            txtDocPractice = itemView.findViewById(R.id.txtDocPractice);
            checkUpdate = itemView.findViewById(R.id.checkUpdate);
            checkView = itemView.findViewById(R.id.checkView);
        }
    }
}
