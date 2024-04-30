package com.robert.sci4202.objects;

public class SettingsCategory {
    public String label, imageLocation;

    public SettingsCategory(String label, String imageLocation) {
        this.imageLocation = imageLocation;
        this.label = label;

    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public String getLabel() {
        return label;
    }
}
