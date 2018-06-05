package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.api.v1.model.CustomerListDTO;
import com.resolutech.services.CustomerService;
import com.resolutech.services.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    // @Important try to use constants so they can be reused.
    // not ending with / allow requests to be done wioth / or not
    // Mappings dont need starting /, its done automatically.
    protected static final String BASE_URL = "/api/v1/customers";

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
        CustomerDTO dto = customerService.getCustomerById(Long.valueOf(id));
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

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO dto = customerService.patchCustomerById(Long.valueOf(id), customerDTO);
        setCustomerUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteCustomerById(Long.valueOf(id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setCustomerUrl(CustomerDTO dto) {
        dto.setCustomerUrl(BASE_URL + "/" + dto.getId());
    }
}
