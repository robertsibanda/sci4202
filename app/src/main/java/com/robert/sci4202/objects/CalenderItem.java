package com.robert.sci4202.objects;

public class CalenderItem {
    private String title, date, time, other_person;


    public CalenderItem(String title, String date, String time,
                        String otherPerson) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.other_person = otherPerson;
        System.out.println("This is calender event : " + date + " -> " + time);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOther_person() {
        return other_person;
    }

    public void setOther_person(String other_person) {
        this.other_person = other_person;
    }
}
