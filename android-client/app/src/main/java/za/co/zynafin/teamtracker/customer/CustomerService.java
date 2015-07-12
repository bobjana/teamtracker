package za.co.zynafin.teamtracker.customer;

import java.util.List;

import retrofit.http.GET;
import za.co.zynafin.teamtracker.customer.Customer;

public interface CustomerService {

    @GET("/api/customers")
    List<Customer> list();

}
