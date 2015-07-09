package za.co.zynafin.teamtracker.trace;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import za.co.zynafin.teamtracker.trace.TracerEvent;

public interface TracerEventService {

    @POST("/api/tracerEvents")
    void saveTracerEvent(@Body TracerEvent tracerEvent, Callback<Void> cb);
}
