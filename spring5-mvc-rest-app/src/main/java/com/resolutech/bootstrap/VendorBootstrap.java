package com.resolutech.bootstrap;

import com.resolutech.domain.Customer;
import com.resolutech.domain.Vendor;
import com.resolutech.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VendorBootstrap implements CommandLineRunner {

    private VendorRepository vendorRepository;

    public VendorBootstrap(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        vendorRepository.save(Vendor.builder().name("Amazon").build());
        vendorRepository.save(Vendor.builder().name("Walmart").build());
        vendorRepository.save(Vendor.builder().name("Zellers").build());
        vendorRepository.save(Vendor.builder().name("Woolco").build());
        vendorRepository.save(Vendor.builder().name("Winners").build());

        log.debug("Vendor loaded: " + vendorRepository.count());
    }
}