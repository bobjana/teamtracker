package za.co.zynafin.teamtracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import za.co.zynafin.teamtracker.domain.TracerEvent;
import za.co.zynafin.teamtracker.domain.User;
import za.co.zynafin.teamtracker.repository.TracerEventRepository;
import za.co.zynafin.teamtracker.repository.UserRepository;
import za.co.zynafin.teamtracker.security.SecurityUtils;
import za.co.zynafin.teamtracker.web.rest.dto.TracerEventDTO;
import za.co.zynafin.teamtracker.web.rest.mapper.TracerEventMapper;
import za.co.zynafin.teamtracker.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TracerEvent.
 */
@RestController
@RequestMapping("/api")
public class TracerEventResource {

    private final Logger log = LoggerFactory.getLogger(TracerEventResource.class);

    @Inject
    private TracerEventRepository tracerEventRepository;

    @Inject
    private TracerEventMapper tracerEventMapper;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /tracerEvents -> Create a new tracerEvent.
     */
    @RequestMapping(value = "/tracerEvents",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody TracerEventDTO tracerEventDTO) throws URISyntaxException {
        log.debug("REST request to save TracerEvent : {}", tracerEventDTO);
        if (tracerEventDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tracerEvent cannot already have an ID").build();
        }
        TracerEvent tracerEvent = tracerEventMapper.tracerEventDTOToTracerEvent(tracerEventDTO);

        Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        if (user.isPresent()){
            tracerEvent.setRepresentativeId(user.get().getId());
        }

        tracerEventRepository.save(tracerEvent);
        return ResponseEntity.created(new URI("/api/tracerEvents/" + tracerEventDTO.getId())).build();
    }

    /**
     * PUT  /tracerEvents -> Updates an existing tracerEvent.
     */
    @RequestMapping(value = "/tracerEvents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody TracerEventDTO tracerEventDTO) throws URISyntaxException {
        log.debug("REST request to update TracerEvent : {}", tracerEventDTO);
        if (tracerEventDTO.getId() == null) {
            return create(tracerEventDTO);
        }
        TracerEvent tracerEvent = tracerEventMapper.tracerEventDTOToTracerEvent(tracerEventDTO);
        tracerEventRepository.save(tracerEvent);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tracerEvents -> get all the tracerEvents.
     */
    @RequestMapping(value = "/tracerEvents",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TracerEventDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<TracerEvent> page = tracerEventRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tracerEvents", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(tracerEventMapper::tracerEventToTracerEventDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /tracerEvents/:id -> get the "id" tracerEvent.
     */
    @RequestMapping(value = "/tracerEvents/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TracerEventDTO> get(@PathVariable Long id) {
        log.debug("REST request to get TracerEvent : {}", id);
        return Optional.ofNullable(tracerEventRepository.findOne(id))
            .map(tracerEventMapper::tracerEventToTracerEventDTO)
            .map(tracerEventDTO -> new ResponseEntity<>(
                tracerEventDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tracerEvents/:id -> delete the "id" tracerEvent.
     */
    @RequestMapping(value = "/tracerEvents/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete TracerEvent : {}", id);
        tracerEventRepository.delete(id);
    }
}
