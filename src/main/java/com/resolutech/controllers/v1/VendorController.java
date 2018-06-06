package com.resolutech.controllers.v1;

import com.resolutech.api.v1.model.VendorDTO;
import com.resolutech.api.v1.model.VendorListDTO;
import com.resolutech.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    public ResponseEntity<VendorListDTO> getAll() {
        List<VendorDTO> list = vendorService.getAll();
        list.forEach(this::setVendorUrl);
        return new ResponseEntity(new VendorListDTO(list), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<VendorDTO> getById(@PathVariable("id") String id) {
        VendorDTO dto = vendorService.getVendorById(Long.valueOf(id));
        setVendorUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.createVendor(vendorDTO);
        setVendorUrl(dto);
        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.saveVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<VendorDTO> patchVendor(@PathVariable("id") String id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO dto = vendorService.patchVendorById(Long.valueOf(id), vendorDTO);
        setVendorUrl(dto);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable("id") String id) {
        vendorService.deleteVendorById(Long.valueOf(id));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setVendorUrl(VendorDTO dto) {
        dto.setVendorUrl(BASE_URL + "/" + dto.getId());
    }
}
