package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.api.v1.model.CustomerListDTO;
import com.resolutech.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/customers/")
public class CustomerController {

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
    public ResponseEntity<CustomerDTO> getByName(@PathVariable("id") String id) {
        CustomerDTO dto = customerService.getById(id);
        setCustomerUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    private void setCustomerUrl(CustomerDTO dto) {
        dto.setCustomerUrl("/api/v1/customers/" + dto.getId());
    }
}
