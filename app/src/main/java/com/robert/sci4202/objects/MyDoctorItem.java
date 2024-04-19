package com.robert.sci4202.objects;

public class MyDoctorItem {

    private String name, imageUrl, contact, profession, hospital, username, aprover;
    private boolean requested, approved;

    public MyDoctorItem(String name, String imageUrl, String contact, String profession, String hospital, String username, String aprover, boolean requested, boolean approved) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.profession = profession;
        this.hospital = hospital;
        this.username = username;
        this.aprover = aprover;
        this.requested = requested;
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }

    public String getAprover() {
        return aprover;
    }

    public void setAprover(String aprover) {
        this.aprover = aprover;
    }
}
