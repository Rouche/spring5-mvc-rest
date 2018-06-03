package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.controllers.v1.CategoryController;
import com.resolutech.controllers.v1.CustomerController;
import com.resolutech.services.CategoryService;
import com.resolutech.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "Joe";
    public static final long LONG_ID = 2L;

    @Mock
    CustomerService customerService;

    // @Important InjectMocks will inject mocks automatically
    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAll() throws Exception {
        CustomerDTO customer1 = CustomerDTO.builder().firstname(NAME).build();
        customer1.setId(1l);

        CustomerDTO customer2 = CustomerDTO.builder().firstname("Bob").build();
        customer2.setId(2l);

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));    }

    @Test
    public void getById() throws Exception {
        CustomerDTO customer1 = CustomerDTO.builder().firstname(NAME).build();
        customer1.setId(12L);

        when(customerService.getById("12")).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void createCustomer() throws Exception {
        //Given
        CustomerDTO customer = CustomerDTO.builder().firstname(NAME).build();

        CustomerDTO returnCustomer = CustomerDTO.builder().firstname(customer.getFirstname()).id(LONG_ID).build();

        when(customerService.createCustomer(customer)).thenReturn(returnCustomer);

        mockMvc.perform(post("/api/v1/customers/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/" + LONG_ID)))
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void saveCustomer() throws Exception {
        //Given
        CustomerDTO customer = CustomerDTO.builder().firstname(NAME).build();

        CustomerDTO returnCustomer = CustomerDTO.builder().firstname(customer.getFirstname()).id(LONG_ID).build();

        when(customerService.saveCustomerById(LONG_ID, customer)).thenReturn(returnCustomer);

        mockMvc.perform(put("/api/v1/customers/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/" + LONG_ID)))
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }
}