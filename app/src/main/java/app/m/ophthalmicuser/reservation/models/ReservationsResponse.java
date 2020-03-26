package app.m.ophthalmicuser.reservation.models;

import com.google.gson.annotations.SerializedName;

import app.m.ophthalmicuser.auth.model.UserData;

public class ReservationsResponse {
    @SerializedName("day")
    private String day;
    @SerializedName("doctor")
    private UserData doctor;
    @SerializedName("note")
    private String note;
    @SerializedName("status")
    private String status;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("time")
    private String time;
    @SerializedName("doc_id")
    private String doc_id;
    @SerializedName("medicine")
    private String medicine;

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public UserData getDoctor() {
        return doctor;
    }

    public void setDoctor(UserData doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
