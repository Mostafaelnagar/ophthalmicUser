package app.m.ophthalmicuser.medicalRecord.models;

import com.google.gson.annotations.SerializedName;

public class XRays {
    @SerializedName("patient_id")
    private String patient_id;
    @SerializedName("x_ray_image")
    private String x_ray_image;
    @SerializedName("x_ray_name")
    private String x_ray_name;
    @SerializedName("x_ray_desc")
    private String x_ray_desc;
    @SerializedName("x_ray_result")
    private String x_ray_result;
    @SerializedName("status")
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getX_ray_desc() {
        return x_ray_desc;
    }

    public void setX_ray_desc(String x_ray_desc) {
        this.x_ray_desc = x_ray_desc;
    }

    public String getX_ray_result() {
        return x_ray_result;
    }

    public void setX_ray_result(String x_ray_result) {
        this.x_ray_result = x_ray_result;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getX_ray_image() {
        return x_ray_image;
    }

    public void setX_ray_image(String x_ray_image) {
        this.x_ray_image = x_ray_image;
    }

    public String getX_ray_name() {
        return x_ray_name;
    }

    public void setX_ray_name(String x_ray_name) {
        this.x_ray_name = x_ray_name;
    }
}
