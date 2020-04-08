package app.m.ophthalmicuser.settings.models;

public class SettingsRequest {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isContactUs() {
        return (message != null);
    }
}
