package za.co.zynafin.teamtracker.repository;

import za.co.zynafin.teamtracker.domain.Customer;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select customer from Customer customer where customer.representative.login = ?#{principal.username}")
    List<Customer> findAllForCurrentUser();

}
