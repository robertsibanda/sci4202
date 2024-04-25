package com.robert.sci4202.objects;

public class Patient {
    private String imageUrl, contact, username, fullName;
    private Boolean canView, canUpdate;


    public Patient(String imageUrl, String contact, String username,
                   String fullName, Boolean canView, Boolean canUpdate) {
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.username = username;
        this.fullName = fullName;
        this.canUpdate = canUpdate;
        this.canView = canView;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanView() {
        return canView;
    }

    public void setCanView(Boolean canView) {
        this.canView = canView;
    }
}
