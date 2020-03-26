package app.m.ophthalmicuser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.m.ophthalmicuser.auth.model.UserData;
import app.m.ophthalmicuser.reservation.models.ReservationsResponse;


public class PassingObject implements Serializable {
    private List<Object> homeData = new ArrayList<>();
    private UserData objectClass;
    private ReservationsResponse reservationsResponse;
    private String object;

    public PassingObject() {
    }

    public PassingObject(String object) {
        this.object = object;
    }

    public PassingObject(List<Object> homeData) {
        this.homeData = homeData;
    }

    public PassingObject(UserData objectClass) {
        this.objectClass = objectClass;
    }

    public PassingObject(ReservationsResponse reservationsResponse) {
        this.reservationsResponse = reservationsResponse;
    }

    public ReservationsResponse getReservationsResponse() {
        return reservationsResponse;
    }

    public void setReservationsResponse(ReservationsResponse reservationsResponse) {
        this.reservationsResponse = reservationsResponse;
    }

    public UserData getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(UserData objectClass) {
        this.objectClass = objectClass;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Object> getHomeData() {
        return homeData;
    }

    public void setHomeData(List<Object> homeData) {
        this.homeData = homeData;
    }
}
