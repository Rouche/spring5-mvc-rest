package com.resolutech.services;

import com.resolutech.api.v1.mapper.CustomerMapper;
import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.domain.Customer;
import com.resolutech.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final Long ID = Long.valueOf("12");
    public static final String NAME = "Zomg";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void testGetAll() {
        //Given
        List<Customer> list = Arrays.asList(Customer.builder().firstname(NAME).build(), Customer.builder().firstname(NAME).build());

        when(customerRepository.findAll()).thenReturn(list);

        //When
        List<CustomerDTO> dtoList = customerService.getAll();

        //Then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
    }

    @Test
    public void testGetById() {

        //Given
        Customer customer = Customer.builder().id(ID).firstname(NAME).build();

        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        //When
        CustomerDTO dto = customerService.getById(ID.toString());

        //Then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
    }

    @Test
    public void createCustomer() {
        //Given
        CustomerDTO customerDTO = CustomerDTO.builder().firstname(NAME).build();

        Customer savedCustomer = Customer.builder().id(ID).firstname(customerDTO.getFirstname()).build();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //When
        CustomerDTO dto = customerService.createCustomer(customerDTO);

        //Then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
    }
}