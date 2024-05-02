package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.ScanQR;
import com.robert.sci4202.ShareRecords;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.SettingsCategory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<SettingsCategory> settingsCategories =
            new ArrayList<>();
    public FragmentManager fragmentManager;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void ShowFragment(Fragment fragment) {
        this.fragmentManager.beginTransaction()
                .replace(R.id.fragHostSettings, fragment)
                .commit();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.txtSettingsLabel.setText(settingsCategories.get(position).getLabel());

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(holder.imgSettings.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        switch (settingsCategories.get(position).getImageLocation()) {
            case "history":
                holder.imgSettings.setImageResource(R.drawable.history_2_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    //load history
                    System.out.println("Showing account history");
                    ShowFragment(new PatientAccountHistory());
                });
                break;
            case "privacy":
                holder.imgSettings.setImageResource(R.drawable.lock_keyhole_minimalistic_unlocked_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    //load settings fragment, Require no password
                });
                break;
            case "security":
                holder.imgSettings.setImageResource(R.drawable.password_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    //load settings change passowrd
                });
                break;
            case "qr":
                if (userData.userType.equals("patient")) {
                    holder.imgSettings.setImageResource(R.drawable.qr_code_svgrepo_com);
                    holder.btnSettingsCategory.setOnClickListener(l -> {
                        System.out.println("Showing QR generator");
                        ShowFragment(new ShareRecords());
                    });
                } else {
                    holder.imgSettings.setImageResource(R.drawable.qr2);
                    holder.txtSettingsLabel.setText("Scan QR");
                    holder.btnSettingsCategory.setOnClickListener(l -> {
                        System.out.println("Showing QR scanner");
                        ShowFragment(new ScanQR());
                    });
                }

                break;
            case "shared":
                holder.imgSettings.setImageResource(R.drawable.qr2);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    //load settings change passowrd
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return settingsCategories.size();
    }

    public void setSettingsCategories(ArrayList<SettingsCategory> settingsCategories) {
        this.settingsCategories = settingsCategories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSettingsLabel;
        ImageView imgSettings;

        ConstraintLayout btnSettingsCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSettingsLabel =
                    itemView.findViewById(R.id.txtSettingsLabel);
            imgSettings = itemView.findViewById(R.id.imageSettingsImage);
            btnSettingsCategory =
                    itemView.findViewById(R.id.btnSettingsCategory);
        }
    }
}
