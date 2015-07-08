package za.co.zynafin.teamtracker.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import za.co.zynafin.teamtracker.domain.Customer;
import za.co.zynafin.teamtracker.domain.User;
import za.co.zynafin.teamtracker.web.rest.dto.CustomerDTO;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper {

    @Mapping(source = "representative.id", target = "representativeId")
//    @Mapping(source = "representative.login", target = "representativeLogin")
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(source = "representativeId", target = "representative")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
