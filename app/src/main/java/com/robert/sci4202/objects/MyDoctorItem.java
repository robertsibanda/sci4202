package com.robert.sci4202.objects;

public class MyDoctorItem {

    private String name, imageUrl, contact, description;

    public MyDoctorItem(String name, String imageUrl, String contact, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
