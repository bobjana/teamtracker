package com.google.android.gms.location.sample.geofencing;

public class AuthToken {

    private String token;
    private Long expires;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
