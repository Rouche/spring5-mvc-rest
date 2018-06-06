package com.resolutech.services;

import com.resolutech.api.v1.model.VendorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public interface VendorService {

    List<VendorDTO> getAll();

    VendorDTO getVendorById(Long id);

    VendorDTO createVendor(VendorDTO vendorDTO);

    VendorDTO saveVendorById(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendorById(Long id, VendorDTO vendorDTO);

    void deleteVendorById(Long id);
}
