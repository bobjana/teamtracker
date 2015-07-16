package za.co.zynafin.teamtracker.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.zynafin.teamtracker.domain.Authority;
import za.co.zynafin.teamtracker.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    @Query("SELECT u FROM User u INNER JOIN u.authorities a WHERE a IN (:authorities)")
    List<User> findByAuthorities(@Param("authorities") Collection<Authority> authorities);

    @Override
    void delete(User t);

}
