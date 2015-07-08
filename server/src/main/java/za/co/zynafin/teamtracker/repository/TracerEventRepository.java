package za.co.zynafin.teamtracker.repository;

import za.co.zynafin.teamtracker.domain.TracerEvent;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TracerEvent entity.
 */
public interface TracerEventRepository extends JpaRepository<TracerEvent,Long> {

}
