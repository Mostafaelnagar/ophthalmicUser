package app.m.ophthalmicuser.settings.models;

import com.google.gson.annotations.SerializedName;

public class DepartmentDetailsModel {
    @SerializedName("department_id")
    private String department_id;
    @SerializedName("image")
    private String image;
    @SerializedName("description")
    private String description;

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
