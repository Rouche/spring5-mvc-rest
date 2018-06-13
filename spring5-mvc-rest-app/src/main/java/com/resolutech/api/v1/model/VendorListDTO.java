package com.resolutech.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class VendorListDTO {
    private final List<VendorDTO> vendors;
}
