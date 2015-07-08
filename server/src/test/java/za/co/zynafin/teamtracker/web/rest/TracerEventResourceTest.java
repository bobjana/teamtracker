package za.co.zynafin.teamtracker.web.rest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import za.co.zynafin.teamtracker.Application;
import za.co.zynafin.teamtracker.domain.TracerEvent;
import za.co.zynafin.teamtracker.repository.TracerEventRepository;
import za.co.zynafin.teamtracker.repository.UserRepository;
import za.co.zynafin.teamtracker.web.rest.mapper.TracerEventMapper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TracerEventResource REST controller.
 *
 * @see TracerEventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TracerEventResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    private static final Long DEFAULT_CUSTOMER_ID = 0L;
    private static final Long UPDATED_CUSTOMER_ID = 1L;

    private static final Long DEFAULT_REPRESENTATIVE_ID = 0L;
    private static final Long UPDATED_REPRESENTATIVE_ID = 1L;
    private static final String DEFAULT_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_TYPE = "UPDATED_TEXT";

    @Inject
    private TracerEventRepository tracerEventRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TracerEventMapper tracerEventMapper;

    private MockMvc restTracerEventMockMvc;

    private TracerEvent tracerEvent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TracerEventResource tracerEventResource = new TracerEventResource();
        ReflectionTestUtils.setField(tracerEventResource, "tracerEventRepository", tracerEventRepository);
        ReflectionTestUtils.setField(tracerEventResource, "userRepository", userRepository);
        ReflectionTestUtils.setField(tracerEventResource, "tracerEventMapper", tracerEventMapper);
        this.restTracerEventMockMvc = MockMvcBuilders.standaloneSetup(tracerEventResource).build();
    }

    @Before
    public void initTest() {
        tracerEvent = new TracerEvent();
        tracerEvent.setDate(DEFAULT_DATE);
        tracerEvent.setCustomerId(DEFAULT_CUSTOMER_ID);
        tracerEvent.setRepresentativeId(DEFAULT_REPRESENTATIVE_ID);
        tracerEvent.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTracerEvent() throws Exception {
        int databaseSizeBeforeCreate = tracerEventRepository.findAll().size();

        // Create the TracerEvent
        restTracerEventMockMvc.perform(post("/api/tracerEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracerEvent)))
                .andExpect(status().isCreated());

        // Validate the TracerEvent in the database
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(databaseSizeBeforeCreate + 1);
        TracerEvent testTracerEvent = tracerEvents.get(tracerEvents.size() - 1);
        assertThat(testTracerEvent.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
        assertThat(testTracerEvent.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
//        assertThat(testTracerEvent.getRepresentativeId()).isEqualTo(DEFAULT_REPRESENTATIVE_ID);
        assertThat(testTracerEvent.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tracerEventRepository.findAll()).hasSize(0);
        // set the field null
        tracerEvent.setDate(null);

        // Create the TracerEvent, which fails.
        restTracerEventMockMvc.perform(post("/api/tracerEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracerEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCustomerIdIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tracerEventRepository.findAll()).hasSize(0);
        // set the field null
        tracerEvent.setCustomerId(null);

        // Create the TracerEvent, which fails.
        restTracerEventMockMvc.perform(post("/api/tracerEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracerEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(tracerEventRepository.findAll()).hasSize(0);
        // set the field null
        tracerEvent.setType(null);

        // Create the TracerEvent, which fails.
        restTracerEventMockMvc.perform(post("/api/tracerEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracerEvent)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllTracerEvents() throws Exception {
        // Initialize the database
        tracerEventRepository.saveAndFlush(tracerEvent);

        // Get all the tracerEvents
        restTracerEventMockMvc.perform(get("/api/tracerEvents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tracerEvent.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
                .andExpect(jsonPath("$.[*].representativeId").value(hasItem(DEFAULT_REPRESENTATIVE_ID.intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getTracerEvent() throws Exception {
        // Initialize the database
        tracerEventRepository.saveAndFlush(tracerEvent);

        // Get the tracerEvent
        restTracerEventMockMvc.perform(get("/api/tracerEvents/{id}", tracerEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tracerEvent.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.representativeId").value(DEFAULT_REPRESENTATIVE_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTracerEvent() throws Exception {
        // Get the tracerEvent
        restTracerEventMockMvc.perform(get("/api/tracerEvents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTracerEvent() throws Exception {
        // Initialize the database
        tracerEventRepository.saveAndFlush(tracerEvent);

		int databaseSizeBeforeUpdate = tracerEventRepository.findAll().size();

        // Update the tracerEvent
        tracerEvent.setDate(UPDATED_DATE);
        tracerEvent.setCustomerId(UPDATED_CUSTOMER_ID);
        tracerEvent.setRepresentativeId(UPDATED_REPRESENTATIVE_ID);
        tracerEvent.setType(UPDATED_TYPE);
        restTracerEventMockMvc.perform(put("/api/tracerEvents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tracerEvent)))
                .andExpect(status().isOk());

        // Validate the TracerEvent in the database
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(databaseSizeBeforeUpdate);
        TracerEvent testTracerEvent = tracerEvents.get(tracerEvents.size() - 1);
        assertThat(testTracerEvent.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
        assertThat(testTracerEvent.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testTracerEvent.getRepresentativeId()).isEqualTo(UPDATED_REPRESENTATIVE_ID);
        assertThat(testTracerEvent.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteTracerEvent() throws Exception {
        // Initialize the database
        tracerEventRepository.saveAndFlush(tracerEvent);

		int databaseSizeBeforeDelete = tracerEventRepository.findAll().size();

        // Get the tracerEvent
        restTracerEventMockMvc.perform(delete("/api/tracerEvents/{id}", tracerEvent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TracerEvent> tracerEvents = tracerEventRepository.findAll();
        assertThat(tracerEvents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
