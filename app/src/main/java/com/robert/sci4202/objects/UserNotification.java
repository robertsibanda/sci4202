package com.robert.sci4202.objects;

public class UserNotification {
    private String notificationType, notificationText, notificationID;

    public UserNotification(String notificationType, String notificationText, String notificationID) {
        this.notificationType = notificationType;
        this.notificationText = notificationText;
        this.notificationID = notificationID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }
}
