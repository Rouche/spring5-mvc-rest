package com.resolutech.api.v1.mapper;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.domain.Category;
import com.resolutech.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Joe";

    @Test
    public void customerToCustomerDTO() {
        //Given
        Customer customer = Customer.builder().id(ID).firstname(NAME).build();

        //When
        CustomerDTO dto = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);

        //Then
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getFirstname());
    }
}