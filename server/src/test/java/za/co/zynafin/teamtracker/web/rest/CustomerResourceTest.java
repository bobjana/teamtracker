package za.co.zynafin.teamtracker.web.rest;

import za.co.zynafin.teamtracker.Application;
import za.co.zynafin.teamtracker.domain.Customer;
import za.co.zynafin.teamtracker.repository.CustomerRepository;
import za.co.zynafin.teamtracker.web.rest.mapper.CustomerMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_PHYSICAL_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_PHYSICAL_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_GEO_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_GEO_LOCATION = "UPDATED_TEXT";

    private static final Integer DEFAULT_COVERAGE = 20;
    private static final Integer UPDATED_COVERAGE = 21;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerMapper customerMapper;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        ReflectionTestUtils.setField(customerResource, "customerMapper", customerMapper);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setName(DEFAULT_NAME);
        customer.setPhysicalAddress(DEFAULT_PHYSICAL_ADDRESS);
        customer.setGeoLocation(DEFAULT_GEO_LOCATION);
        customer.setCoverage(DEFAULT_COVERAGE);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getPhysicalAddress()).isEqualTo(DEFAULT_PHYSICAL_ADDRESS);
        assertThat(testCustomer.getGeoLocation()).isEqualTo(DEFAULT_GEO_LOCATION);
        assertThat(testCustomer.getCoverage()).isEqualTo(DEFAULT_COVERAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkPhysicalAddressIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);
        // set the field null
        customer.setPhysicalAddress(null);

        // Create the Customer, which fails.
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkGeoLocationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);
        // set the field null
        customer.setGeoLocation(null);

        // Create the Customer, which fails.
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCoverageIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);
        // set the field null
        customer.setCoverage(null);

        // Create the Customer, which fails.
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].physicalAddress").value(hasItem(DEFAULT_PHYSICAL_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].geoLocation").value(hasItem(DEFAULT_GEO_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].coverage").value(hasItem(DEFAULT_COVERAGE)));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.physicalAddress").value(DEFAULT_PHYSICAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.geoLocation").value(DEFAULT_GEO_LOCATION.toString()))
            .andExpect(jsonPath("$.coverage").value(DEFAULT_COVERAGE));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        customer.setName(UPDATED_NAME);
        customer.setPhysicalAddress(UPDATED_PHYSICAL_ADDRESS);
        customer.setGeoLocation(UPDATED_GEO_LOCATION);
        customer.setCoverage(UPDATED_COVERAGE);
        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getPhysicalAddress()).isEqualTo(UPDATED_PHYSICAL_ADDRESS);
        assertThat(testCustomer.getGeoLocation()).isEqualTo(UPDATED_GEO_LOCATION);
        assertThat(testCustomer.getCoverage()).isEqualTo(UPDATED_COVERAGE);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
