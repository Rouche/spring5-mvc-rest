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

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerById(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomerById(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
