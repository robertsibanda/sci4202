package com.robert.sci4202.objects;

public class DoctorAppointment {
    private String doctorName, doctorProfession, appointmentDate,
            getAppointmentTime, appointmendDescription, approver,
            patientID;
    private boolean approved, rejected;

    public DoctorAppointment(String doctorName, String doctorProfession,
                             String appointmentDate,
                             String getAppointmentTime,
                             String appointmendDescription,
                             String approver, String patientID,
                             boolean approved,
                             boolean rejected) {
        this.doctorName = doctorName;
        this.doctorProfession = doctorProfession;
        this.appointmentDate = appointmentDate;
        this.getAppointmentTime = getAppointmentTime;
        this.appointmendDescription = appointmendDescription;
        this.approver = approver;
        this.patientID = patientID;
        this.approved = approved;
        this.rejected = rejected;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorProfession() {
        return doctorProfession;
    }

    public void setDoctorProfession(String doctorProfession) {
        this.doctorProfession = doctorProfession;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getGetAppointmentTime() {
        return getAppointmentTime;
    }

    public void setGetAppointmentTime(String getAppointmentTime) {
        this.getAppointmentTime = getAppointmentTime;
    }

    public String getAppointmendDescription() {
        return appointmendDescription;
    }

    public void setAppointmendDescription(String appointmendDescription) {
        this.appointmendDescription = appointmendDescription;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
