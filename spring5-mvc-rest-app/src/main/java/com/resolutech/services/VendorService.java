package com.resolutech.services;

//import com.resolutech.api.v1.model.VendorDTO;
import com.resolutech.model.VendorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public interface VendorService {

    List<VendorDTO> getAll();

    VendorDTO getVendorById(Long id);

    VendorDTO createVendor(VendorDTO vendor);

    VendorDTO saveVendorById(Long id, VendorDTO vendor);

    VendorDTO patchVendorById(Long id, VendorDTO vendor);

    void deleteVendorById(Long id);
}
