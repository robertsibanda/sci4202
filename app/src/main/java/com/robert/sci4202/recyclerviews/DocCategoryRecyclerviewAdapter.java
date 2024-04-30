package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.DocCategory;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocCategoryRecyclerviewAdapter extends RecyclerView.Adapter<DocCategoryRecyclerviewAdapter.ViewHolder> {

    private ArrayList<DocCategory> docCategories = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doc_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        holder.txtProfession.setText(docCategories.get(position).getProfession());
        holder.txtFullName.setText(docCategories.get(position).getFullName());
        holder.userID = docCategories.get(position).getUserID();
        holder.btnViewProfile.setOnClickListener(l -> {
            // view doctor profile
            // user userID
        });
    }

    @Override
    public int getItemCount() {
        return docCategories.size();
    }

    public void setDocCategories(ArrayList<DocCategory> docCategories) {
        this.docCategories = docCategories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        String userID;
        TextView txtFullName, txtProfession;

        Button btnViewProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txtDoctorName);
            txtProfession =
                    itemView.findViewById(R.id.txtDoctorProffession);
            btnViewProfile = itemView.findViewById(R.id.btnViewDocProfile);
        }
    }
}
