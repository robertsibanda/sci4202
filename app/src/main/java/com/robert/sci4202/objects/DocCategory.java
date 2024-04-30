package com.robert.sci4202.objects;

public class DocCategory {

    private String fullName, profession, userID;

    public DocCategory(String fullName, String profession, String userID) {
        this.fullName = fullName;
        this.profession = profession;
        this.userID = userID;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
