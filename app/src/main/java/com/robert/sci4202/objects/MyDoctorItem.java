package com.robert.sci4202.objects;

public class MyDoctorItem {

    private String name;
    private String imageUrl;

    private String contact;
    private String profession;
    private String organisation;
    private String userId;
    private boolean canView, canUpdate;

    public MyDoctorItem(String name, String imageUrl, String contact,
                        String profession, String hospital,
                        String userId,
                        boolean canView, boolean canUpdate) {

        this.name = name;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.profession = profession;
        this.organisation = hospital;
        this.userId = userId;
        this.canView = canView;
        this.canUpdate = canUpdate;
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

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

}
