package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.VendorDTO;
import com.resolutech.api.v1.model.VendorListDTO;
import com.resolutech.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAll() {
        List<VendorDTO> list = vendorService.getAll();
        list.forEach(this::setVendorUrl);
        return new VendorListDTO(list);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getById(@PathVariable("id") String id) {
        VendorDTO dto = vendorService.getVendorById(Long.valueOf(id));
        setVendorUrl(dto);
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.createVendor(vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.saveVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.patchVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return dto;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable("id") String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }

    private void setVendorUrl(VendorDTO dto) {
        dto.setVendorUrl(BASE_URL + "/" + dto.getId());
    }
}
