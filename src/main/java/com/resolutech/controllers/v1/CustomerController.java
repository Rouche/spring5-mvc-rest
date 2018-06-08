package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.CustomerDTO;
import com.resolutech.api.v1.model.CustomerListDTO;
import com.resolutech.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Important Swagger configruration options (search io.swagger.annotations.)
// Blog (With security config) : http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//
// Swagger editor: https://swagger.io/tools/swagger-editor/
// Generate servers works but you need to use F12 to get the link to the failing URL because of mixed content.
// (http/https) Then you will be able to download it.
@Api(description = "Customer controller description zomg")
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

    @ApiOperation(value = "This will get the list of all customers", notes = "Some notes.")
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
