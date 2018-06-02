package com.resolutech.controllers;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final String NAME = "Joe";

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
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1l);
        customer1.setFirstname(NAME);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2l);
        customer2.setFirstname("Bob");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));    }

    @Test
    public void getById() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(12L);
        customer1.setFirstname(NAME);

        when(customerService.getById("12")).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(NAME)));
    }
}