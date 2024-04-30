package com.robert.sci4202.objects;

public class DoctorAppointment {
    private String doctorName, doctorProfession, appointmentDate,
            getAppointmentTime, appointmendDescription;
    private boolean approved;

    public DoctorAppointment(String doctorName, String doctorProfession,
                             String appointmentDate,
                             String getAppointmentTime,
                             String appointmendDescription,
                             boolean approved) {
        this.doctorName = doctorName;
        this.doctorProfession = doctorProfession;
        this.appointmentDate = appointmentDate;
        this.getAppointmentTime = getAppointmentTime;
        this.appointmendDescription = appointmendDescription;
        this.approved = approved;
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
}
