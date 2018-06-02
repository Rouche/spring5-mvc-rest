package com.resolutech.services;

import com.resolutech.api.v1.mapper.CustomerMapper;
import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.domain.Customer;
import com.resolutech.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jealar2 on 2018-05-31
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAll() {
        return customerRepository.findAll()
            .stream()
            .map(customerMapper::customerToCustomerDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getById(String id) {

        Optional<Customer> customerOptional = customerRepository.findById(Long.valueOf(id));

        if(!customerOptional.isPresent()) {
            throw new RuntimeException("Customer not found: [" + id + "]");
        }

        return customerMapper.customerToCustomerDTO(customerOptional.get());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        customer = customerRepository.save(customer);

        customerDTO = customerMapper.customerToCustomerDTO(customer);

        return customerDTO;
    }
}
