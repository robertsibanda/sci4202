package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robert.sci4202.PatientAccountHistory;
import com.robert.sci4202.R;
import com.robert.sci4202.ScanQR;
import com.robert.sci4202.SettingsPrivacyFragment;
import com.robert.sci4202.SettingsSecurityFragment;
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
                holder.imgSettings.setImageResource(R.drawable.pratt_and_whitney_certificate_of_deposit_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    //load history
                    System.out.println("Showing account history");
                    ShowFragment(new PatientAccountHistory());
                });
                break;
            case "privacy":
                holder.imgSettings.setImageResource(R.drawable.shield_user_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    ShowFragment(new SettingsPrivacyFragment());
                });
                break;
            case "security":
                holder.imgSettings.setImageResource(R.drawable.guard_lock_padlock_svgrepo_com);
                holder.btnSettingsCategory.setOnClickListener(l -> {
                    ShowFragment(new SettingsSecurityFragment());
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
