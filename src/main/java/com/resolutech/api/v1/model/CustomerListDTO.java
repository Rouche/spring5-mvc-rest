package com.resolutech.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomerListDTO {
    private final List<CustomerDTO> customers;
}
