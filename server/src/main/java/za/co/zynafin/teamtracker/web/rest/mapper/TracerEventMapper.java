package za.co.zynafin.teamtracker.web.rest.mapper;

import za.co.zynafin.teamtracker.domain.*;
import za.co.zynafin.teamtracker.web.rest.dto.TracerEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TracerEvent and its DTO TracerEventDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TracerEventMapper {

    TracerEventDTO tracerEventToTracerEventDTO(TracerEvent tracerEvent);

    TracerEvent tracerEventDTOToTracerEvent(TracerEventDTO tracerEventDTO);
}
