package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.robert.sci4202.HomeFragment;
import com.robert.sci4202.R;
import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.DoctorAppointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAppointmentRecyclerviewAdapter extends RecyclerView.Adapter<DoctorAppointmentRecyclerviewAdapter.ViewHolder> {

    public Fragment fragment;
    private ArrayList<DoctorAppointment> doctorAppointments =
            new ArrayList<>();

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

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(holder.btnDate.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        holder.txtDocfullName.setText(doctorAppointments.get(position).getDoctorName());
        holder.txtAppointmentDescription.setText(doctorAppointments.get(position).getAppointmendDescription());
        holder.txtDocProfession.setText(doctorAppointments.get(position).getDoctorProfession());
        holder.btnTime.setText(doctorAppointments.get(position).getGetAppointmentTime());
        holder.btnDate.setText(doctorAppointments.get(position).getAppointmentDate());

        boolean approved = doctorAppointments.get(position).isApproved();
        boolean rejected = doctorAppointments.get(position).isRejected();

        if (approved) {
            holder.imgReject.setVisibility(View.GONE);
            holder.imgApproved.setImageDrawable(holder.imgApproved.getResources().getDrawable(R.drawable.calendar_approved));
        } else if (rejected) {
            holder.imgApproved.setImageDrawable(holder.btnTime.getResources().getDrawable(R.drawable.calendar_reject_2));
            holder.imgReject.setVisibility(View.GONE);
        } else {
            String approver =
                    doctorAppointments.get(position).getApprover();
            if (userData.userID.equals(approver)) {
                holder.txtAppointmentApproved.setVisibility(View.GONE);
                holder.imgApproved.setImageDrawable(holder.imgApproved.getResources().getDrawable(R.drawable.calendar_accept));
                holder.imgReject.setImageDrawable(holder.imgApproved.getResources().getDrawable(R.drawable.calendar_reject));

                holder.imgApproved.setOnClickListener(l -> {
                    new Thread(() -> {
                        // accept appointment

                        DoctorAppointment appointment =
                                doctorAppointments.get(position);

                        Map<String, String> params = new HashMap<>();

                        params.put("doctor", userData.userID);
                        params.put("update", "approve");
                        params.put("patient", appointment.getPatientID());
                        params.put("time",
                                appointment.getGetAppointmentTime());
                        params.put("date",
                                appointment.getAppointmentDate());

                        try {
                            ServerResult result = RPCRequests.sendRequest(
                                    "update_appointment",
                                    params);
                            System.out.println("Result : " + result.getResult());
                            try {
                                result.getResult().get("success");
                            } catch (Exception ex) {
                                try {
                                    Toast.makeText(holder.imgReject.getContext(),
                                            result.getResult().get(
                                                    "error").toString(),
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(holder.imgReject.getContext(),
                                            e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            System.out.println(result.getResult());

                            fragment.getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    ShowFragment(new HomeFragment());
                                }
                            });
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }).start();
                });


                holder.imgReject.setOnClickListener(l -> {
                    new Thread(() -> {
                        // accept appointment

                        DoctorAppointment appointment =
                                doctorAppointments.get(position);

                        Map<String, String> params = new HashMap<>();

                        params.put("doctor", userData.userID);
                        params.put("patient", appointment.getPatientID());
                        params.put("update", "reject");
                        params.put("time",
                                appointment.getGetAppointmentTime());
                        params.put("date",
                                appointment.getAppointmentDate());
                        try {
                            ServerResult result = RPCRequests.sendRequest(
                                    "update_appointment",
                                    params);
                            System.out.println("Result : " + result.getResult());
                            try {
                                result.getResult().get("success");
                            } catch (Exception ex) {
                                try {
                                    Toast.makeText(holder.imgReject.getContext(),
                                            result.getResult().get(
                                                    "error").toString(),
                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(holder.imgReject.getContext(),
                                            e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            System.out.println(result.getResult());

                            fragment.getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    ShowFragment(new HomeFragment());
                                }
                            });
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }).start();
                });

            } else {
                //user not approver
                holder.imgApproved.setImageDrawable(holder.imgApproved.getResources().getDrawable(R.drawable.calendar_pending));
                holder.imgReject.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public int getItemCount() {
        return doctorAppointments.size();
    }

    public void setDoctorAppointments(ArrayList<DoctorAppointment> doctorAppointments) {
        this.doctorAppointments = doctorAppointments;
    }

    public void ShowFragment(Fragment fragment) {
        this.fragment.getParentFragmentManager().beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .commit();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtDocfullName, txtDocProfession,
                txtAppointmentDescription, txtAppointmentApproved;

        TextView btnDate, btnTime;

        ImageView imgApproved, imgReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDocfullName = itemView.findViewById(R.id.txtDoctorName);
            txtDocProfession =
                    itemView.findViewById(R.id.txtDoctorProffession);
            txtAppointmentDescription =
                    itemView.findViewById(R.id.txtAppointmentDescription);

            txtAppointmentApproved =
                    itemView.findViewById(R.id.txtAppointmentApproved);

            btnDate = itemView.findViewById(R.id.btnAppointmentDate);
            btnTime = itemView.findViewById(R.id.btnAppointmentTime);

            imgApproved = itemView.findViewById(R.id.imgApproved);
            imgReject = itemView.findViewById(R.id.imgReject);
        }
    }
}
