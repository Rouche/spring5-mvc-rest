package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.api.v1.model.CustomerListDTO;
import com.resolutech.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(CustomerController.CUSTOMER_ROOT_URL)
public class CustomerController {

    static final String CUSTOMER_ROOT_URL = "/api/v1/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAll() {
        List<CustomerDTO> list = customerService.getAll();
        list.forEach(this::setCustomerUrl);
        return new ResponseEntity(new CustomerListDTO(list), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable("id") String id) {
        CustomerDTO dto = customerService.getById(id);
        setCustomerUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO dto = customerService.createCustomer(customerDTO);
        setCustomerUrl(dto);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO dto = customerService.saveCustomerById(Long.valueOf(id), customerDTO);
        setCustomerUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    private void setCustomerUrl(CustomerDTO dto) {
        dto.setCustomerUrl(CUSTOMER_ROOT_URL + "/" + dto.getId());
    }
}
