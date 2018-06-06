package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.VendorDTO;
import com.resolutech.controllers.RestResponseEntityExceptionHandler;
import com.resolutech.services.VendorService;
import com.resolutech.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "Joe";
    public static final long LONG_ID = 2L;

    @Mock
    VendorService vendorService;

    // @Important InjectMocks will inject mocks automatically
    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAll() throws Exception {
        VendorDTO vendor1 = VendorDTO.builder().name(NAME).build();
        vendor1.setId(1l);

        VendorDTO vendor2 = VendorDTO.builder().name("Bob").build();
        vendor2.setId(2l);

        List<VendorDTO> vendors = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAll()).thenReturn(vendors);

        mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));    }

    @Test
    public void getById() throws Exception {
        VendorDTO vendor1 = VendorDTO.builder().name(NAME).build();
        vendor1.setId(12L);

        when(vendorService.getVendorById(LONG_ID)).thenReturn(vendor1);

        mockMvc.perform(get("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testCreateVendor() throws Exception {
        //Given
        VendorDTO vendor = VendorDTO.builder().name(NAME).build();

        VendorDTO returnVendor = VendorDTO.builder().name(vendor.getName()).id(LONG_ID).build();

        when(vendorService.createVendor(vendor)).thenReturn(returnVendor);

        mockMvc.perform(post("/api/v1/vendors/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        //Given
        VendorDTO vendor = VendorDTO.builder().name(NAME).build();

        VendorDTO returnVendor = VendorDTO.builder().name(vendor.getName()).id(LONG_ID).build();

        when(vendorService.saveVendorById(LONG_ID, vendor)).thenReturn(returnVendor);

        mockMvc.perform(put("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testPatchVendor() throws Exception {
        //Given
        String FIRSTNAME = "Mamaaaaannnnn";
        VendorDTO vendor = VendorDTO.builder().name(FIRSTNAME).build();

        VendorDTO returnVendor = VendorDTO.builder().name(vendor.getName()).id(LONG_ID).build();

        when(vendorService.patchVendorById(LONG_ID, vendor)).thenReturn(returnVendor);

        mockMvc.perform(patch("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(FIRSTNAME)));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        //Given
        mockMvc.perform(delete("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(LONG_ID);
    }


    @Test
    public void testByIdNotFound() throws Exception {
        when(vendorService.getVendorById(LONG_ID)).thenThrow(new ResourceNotFoundException());

        mockMvc.perform(get("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}