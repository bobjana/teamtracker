package com.google.android.gms.location.sample.geofencing;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface TracerEventService {

    @POST("/api/tracerEvents")
    void saveTracerEvent(@Body TracerEvent tracerEvent, Callback<Void> cb);
}
