package com.robert.sci4202.objects;

public class Patient {
    private String imageUrl, contact, username, fullName, approver;
    private Boolean requested, approved;


    public Patient(String imageUrl, String contact, String username, String fullName, String approver, Boolean requested, Boolean approved) {
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.username = username;
        this.fullName = fullName;
        this.approver = approver;
        this.requested = requested;
        this.approved = approved;
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

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
}
