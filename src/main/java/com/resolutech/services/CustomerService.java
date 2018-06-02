package com.resolutech.services;

import com.resolutech.api.v1.model.CategoryDTO;
import com.resolutech.api.v1.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public interface CustomerService {

    List<CustomerDTO> getAll();

    CustomerDTO getById(String id);

    CustomerDTO createCustomer(CustomerDTO customer);
}
