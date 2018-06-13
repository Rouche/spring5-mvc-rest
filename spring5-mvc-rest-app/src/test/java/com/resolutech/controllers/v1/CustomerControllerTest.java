package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.controllers.RestResponseEntityExceptionHandler;
import com.resolutech.services.CustomerService;
import com.resolutech.services.ResourceNotFoundException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "Joe";
    public static final long LONG_ID = 2L;

    @Mock
    CustomerService customerService;

    // @Important InjectMocks will inject mocks automatically inside customerController (the customerService)
    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
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
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));    }

    @Test
    public void getById() throws Exception {
        CustomerDTO customer1 = CustomerDTO.builder().firstname(NAME).build();
        customer1.setId(12L);

        when(customerService.getCustomerById(LONG_ID)).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        //Given
        CustomerDTO customer = CustomerDTO.builder().firstname(NAME).build();

        CustomerDTO returnCustomer = CustomerDTO.builder().firstname(customer.getFirstname()).id(LONG_ID).build();

        when(customerService.createCustomer(customer)).thenReturn(returnCustomer);

        mockMvc.perform(post("/api/v1/customers/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/" + LONG_ID)))
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //Given
        CustomerDTO customer = CustomerDTO.builder().firstname(NAME).build();

        CustomerDTO returnCustomer = CustomerDTO.builder().firstname(customer.getFirstname()).id(LONG_ID).build();

        when(customerService.saveCustomerById(LONG_ID, customer)).thenReturn(returnCustomer);

        mockMvc.perform(put("/api/v1/customers/" + LONG_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/" + LONG_ID)))
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        //Given
        String FIRSTNAME = "Mamaaaaannnnn";
        CustomerDTO customer = CustomerDTO.builder().firstname(FIRSTNAME).build();

        CustomerDTO returnCustomer = CustomerDTO.builder().firstname(customer.getFirstname()).id(LONG_ID).build();

        when(customerService.patchCustomerById(LONG_ID, customer)).thenReturn(returnCustomer);

        mockMvc.perform(patch("/api/v1/customers/" + LONG_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/" + LONG_ID)))
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        //Given
        mockMvc.perform(delete("/api/v1/customers/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(LONG_ID);
    }


    @Test
    public void testByIdNotFound() throws Exception {
        when(customerService.getCustomerById(LONG_ID)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/api/v1/customers/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}