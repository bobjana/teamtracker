package za.co.zynafin.teamtracker.trace;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface TracerEventService {

    @POST("/api/tracerEvents")
    void saveTracerEvent(@Body Trace trace, Callback<Void> cb);
}
