package za.co.zynafin.teamtracker.sync;

import java.util.List;

import retrofit.http.GET;

public interface CustomerService {

    @GET("/api/customers")
    List<Customer> list();

}
