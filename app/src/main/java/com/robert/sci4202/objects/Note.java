package com.robert.sci4202.objects;

public class Note {

    private String date, notes, author;

    public Note(String date, String notes, String author) {
        this.date = date;
        this.notes = notes;
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
