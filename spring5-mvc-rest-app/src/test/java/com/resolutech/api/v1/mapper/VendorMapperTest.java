package com.resolutech.api.v1.mapper;

import com.resolutech.domain.Vendor;
import com.resolutech.model.VendorDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Joe";

    @Test
    public void customerToVendorDTO() {
        //Given
        Vendor customer = Vendor.builder().id(ID).name(NAME).build();

        //When
        VendorDTO dto = VendorMapper.INSTANCE.vendorToVendorDTO(customer);

        //Then
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }
}