package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.VendorDTO;
import com.resolutech.api.v1.model.VendorListDTO;
import com.resolutech.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Vendor controller description zomg")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    // @Important try to use constants so they can be reused.
    // not ending with / allow requests to be done wioth / or not
    // Mappings dont need starting /, its done automatically.
    protected static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "This will get the list of all vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAll() {
        List<VendorDTO> list = vendorService.getAll();
        list.forEach(this::setVendorUrl);
        return new VendorListDTO(list);
    }

    @ApiOperation(value = "This will get a vendor by id", notes = "404 if not found")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getById(@PathVariable("id") String id) {
        VendorDTO dto = vendorService.getVendorById(Long.valueOf(id));
        setVendorUrl(dto);
        return dto;
    }

    @ApiOperation(value = "This will create a vendor", notes = "201 When succesful")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.createVendor(vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @ApiOperation(value = "This will replace vendor with the id by new data")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.saveVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @ApiOperation(value = "This will replace vendor with the id with new properties values if set")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.patchVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @ApiOperation(value = "This will delete the vendor with the id")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable("id") String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }

    private void setVendorUrl(VendorDTO dto) {
        dto.setVendorUrl(BASE_URL + "/" + dto.getId());
    }
}
