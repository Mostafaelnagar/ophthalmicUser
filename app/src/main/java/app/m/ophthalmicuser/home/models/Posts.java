package app.m.ophthalmicuser.home.models;

import com.google.gson.annotations.SerializedName;

import app.m.ophthalmicuser.auth.model.UserData;

public class Posts {
    @SerializedName("post_content")
    private String post_content;
    @SerializedName("post_date")
    private String post_date;
    @SerializedName("post_author")
    private UserData post_author;

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public UserData getPost_author() {
        return post_author;
    }

    public void setPost_author(UserData post_author) {
        this.post_author = post_author;
    }
}
