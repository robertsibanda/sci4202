package com.robert.sci4202.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robert.sci4202.R;
import com.robert.sci4202.objects.Note;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecyclerviewAdapter
        extends RecyclerView.Adapter<NotesRecyclerviewAdapter.ViewHolder>{

    private ArrayList<Note> notes = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.note_list_item, parent, false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtContent.setText(notes.get(position).getNotes());
        holder.txtAuthor.setText(notes.get(position).getAuthor());
        holder.txtDate.setText(notes.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtAuthor, txtContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDateRecorded);
            txtAuthor = itemView.findViewById(R.id.txtNoteAuthor);
            txtContent = itemView.findViewById(R.id.txtNoteText);
        }
    }
}
