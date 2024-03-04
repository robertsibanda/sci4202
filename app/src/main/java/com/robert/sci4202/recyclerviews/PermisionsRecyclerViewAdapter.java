package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.Permission;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PermisionsRecyclerViewAdapter extends RecyclerView.Adapter<PermisionsRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Permission> permissions = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.permission_list_item, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtPerm.setText(permissions.get(position).getPermissionString());
        //holder.checkBox.setText(permissions.get(position).getPermission());
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Spinner checkBox;
        TextView txtPerm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.chkPermCheck);
            txtPerm = itemView.findViewById(R.id.txtPermString);
        }
    }
}
