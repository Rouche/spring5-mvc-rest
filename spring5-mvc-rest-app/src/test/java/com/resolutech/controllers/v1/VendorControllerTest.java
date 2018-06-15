package com.resolutech.controllers.v1;

import com.resolutech.model.ObjectFactory;
import com.resolutech.model.VendorDTO;
import com.resolutech.services.ResourceNotFoundException;
import com.resolutech.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @Important different way to test with MockBean when @WenMvcTest is used
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "Joe";
    public static final Long LONG_ID = 2l;

    @MockBean
    VendorService vendorService; // provided by spring context

    @Autowired
    MockMvc mockMvc; // provided by spring context

    ObjectFactory factory;
    private VendorDTO vendor1;
    private VendorDTO vendor2;

    @Before
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this); no need with MockBean
        factory = new ObjectFactory();

        vendor1 = factory.createVendorDTO();
        vendor1.setName(NAME);
        vendor1.setId(1l);

        vendor2 = factory.createVendorDTO();
        vendor2.setName("Bob");
        vendor2.setId(2l);

        //mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
        //      .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAll() throws Exception {
        List<VendorDTO> vendors = Arrays.asList(vendor1, vendor2);

        when(vendorService.getAll()).thenReturn(vendors);

        mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));    }

    @Test
    public void getById() throws Exception {
        when(vendorService.getVendorById(LONG_ID)).thenReturn(vendor1);

        mockMvc.perform(get("/api/v1/vendors/" + LONG_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testCreateVendor() throws Exception {

        VendorDTO returnVendor = factory.createVendorDTO();
        returnVendor.setName(vendor1.getName());
        returnVendor.setId(LONG_ID);

        when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(post("/api/v1/vendors/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendor1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testUpdateVendor() throws Exception {

        VendorDTO returnVendor = factory.createVendorDTO();
        returnVendor.setName(vendor1.getName());
        returnVendor.setId(LONG_ID);

        when(vendorService.saveVendorById(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(put("/api/v1/vendors/" + LONG_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void testPatchVendor() throws Exception {
        //Given
        String NAME = "Mamaaaaannnnn";

        VendorDTO vendorPatch = factory.createVendorDTO();
        vendorPatch.setName(NAME);
        vendorPatch.setId(LONG_ID);

        VendorDTO returnVendor = factory.createVendorDTO();
        returnVendor.setName(vendorPatch.getName());
        returnVendor.setId(LONG_ID);

        // @Important Mockito matcher with JAXB class seems to not be working. NEed to use any(...)
        when(vendorService.patchVendorById(anyLong(), any(VendorDTO.class))).thenReturn(returnVendor);

        mockMvc.perform(patch("/api/v1/vendors/" + LONG_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(vendorPatch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/" + LONG_ID)))
                .andExpect(jsonPath("$.name", equalTo(NAME)));
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