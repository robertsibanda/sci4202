package com.robert.sci4202.objects;

public class AppointmentDay {

    private String day, date;
    private boolean selected;


    public AppointmentDay(String day, String date, boolean selected) {
        this.day = day;
        this.date = date;
        this.selected = selected;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
