package com.google.android.gms.location.sample.geofencing;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

public interface LoginService {

    @POST("/api/authenticate")
    AuthToken authenticate(@Query("username") String username, @Query("password")String password);

}
