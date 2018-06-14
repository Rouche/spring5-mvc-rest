package com.resolutech.services;

import com.resolutech.api.v1.mapper.VendorMapper;
import com.resolutech.domain.Vendor;
import com.resolutech.model.ObjectFactory;
import com.resolutech.model.VendorDTO;
import com.resolutech.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    public static final Long ID = Long.valueOf("12");
    public static final String NAME = "Zomg";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    ObjectFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new ObjectFactory();

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void testGetAll() {
        //Given
        List<Vendor> list = Arrays.asList(Vendor.builder().name(NAME).build(), Vendor.builder().name(NAME).build());

        when(vendorRepository.findAll()).thenReturn(list);

        //When
        List<VendorDTO> dtoList = vendorService.getAll();

        //Then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
    }

    @Test
    public void testGetById() {

        //Given
        Vendor vendor = Vendor.builder().id(ID).name(NAME).build();

        // @Important Mockito BDD syntax
        given(vendorRepository.findById(ID)).willReturn(Optional.of(vendor));

        //When
        VendorDTO dto = vendorService.getVendorById(ID);

        //Then
        then(vendorRepository).should(times(1)).findById(ID);
        //junit assert that with matchers
        assertThat(dto.getName(), is(equalTo(NAME)));
    }

    @Test
    public void testCreateVendor() {
        //Given
        VendorDTO vendorDTO = factory.createVendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = Vendor.builder().id(ID).name(vendorDTO.getName()).build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //When
        VendorDTO dto = vendorService.createVendor(vendorDTO);

        //Then
        assertNotNull(dto);
        assertEquals(NAME, dto.getName());
    }

    @Test
    public void testSaveVendorById() {
        //Given
        VendorDTO vendorDTO = factory.createVendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = Vendor.builder().id(ID).name(vendorDTO.getName()).build();

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //When
        VendorDTO dto = vendorService.saveVendorById(ID, vendorDTO);

        //Then
        assertNotNull(dto);
        assertEquals(NAME, dto.getName());
    }

    @Test
    public void deleteVendorById() {
        //Given

        //When
        vendorService.deleteVendorById(ID);

        //Then
        verify(vendorRepository, times(1)).deleteById(ID);
    }
}