package com.robert.sci4202.objects;

public class Allege {
    public String allege, note, date, reaction;

    public Allege(String allege, String note, String date,
                  String reaction) {
        this.note = note;
        this.reaction = reaction;
        this.date = date;
        this.allege = allege;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAllege() {
        return allege;
    }

    public void setAllege(String allege) {
        this.allege = allege;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getReaction() {
        return reaction;
    }
}
