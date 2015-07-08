package za.co.zynafin.teamtracker.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import za.co.zynafin.teamtracker.account.User;
import za.co.zynafin.teamtracker.sync.GeoFence;

public class GeoFenceEvent implements Serializable {

    @JsonIgnore
    private Long id;
    private String transitionType;
    private GeoFence geoFence;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'hh:mm:ss.SSSZ")
    private Date date;
    private User user;

    public GeoFenceEvent() { }

    public GeoFenceEvent(String transitionType, GeoFence geoFence) {
        this.transitionType = transitionType;
        this.date = new Date();
        this.geoFence = geoFence;
    }

    public GeoFenceEvent(String transitionType, GeoFence geoFence, Date date) {
        this.transitionType = transitionType;
        this.date = date;
        this.geoFence = geoFence;
    }

    public String getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(String transitionType) {
        this.transitionType = transitionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public GeoFence getGeoFence() {
        return geoFence;
    }

    public void setGeoFence(GeoFence geoFence) {
        this.geoFence = geoFence;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;

        GeoFenceEvent event = (GeoFenceEvent) o;

        if (geoFence != null ? !geoFence.equals(event.geoFence) : event.geoFence != null)
            return false;
        if (transitionType != null ? !transitionType.equals(event.transitionType) : event.transitionType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transitionType != null ? transitionType.hashCode() : 0;
        result = 31 * result + (geoFence != null ? geoFence.hashCode() : 0);
        return result;
    }
}
