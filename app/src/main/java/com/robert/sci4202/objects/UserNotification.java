package com.robert.sci4202.objects;

public class UserNotification {
    private String notificationTitle, notificationType, notificationID, notificationContent, other_;

    public UserNotification(String notificationType, String notificationTitle, String notificationID, String notificationContent, String other) {
        this.notificationTitle = notificationTitle;
        this.notificationID = notificationID;
        this.notificationContent = notificationContent;
        this.notificationType = notificationType;
        this.other_ = other;
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "notificationTitle='" + notificationTitle + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", notificationID='" + notificationID + '\'' +
                ", notificationContent='" + notificationContent + '\'' +
                ", other_='" + other_ + '\'' +
                '}';
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getOther_() {
        return other_;
    }

    public void setOther_(String other_) {
        this.other_ = other_;
    }
}
