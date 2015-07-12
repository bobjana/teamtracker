package za.co.zynafin.teamtracker.trace;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface TraceService {

    @POST("/api/tracerEvents")
    void save(@Body Trace trace, Callback<Void> cb);
}
