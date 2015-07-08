package za.co.zynafin.teamtracker.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GeoFence {

    private Long id;
    private double lat;
    private double lng;
    private float radius;

    public GeoFence() {
    }

    public GeoFence(Long id) {
        this.id = id;
    }

    public GeoFence(Long id, double lat, double lng, float radius) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "GeoFence{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", radius=" + radius +
                '}';
    }
}
