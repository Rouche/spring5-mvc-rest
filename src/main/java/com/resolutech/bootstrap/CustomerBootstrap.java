package com.resolutech.bootstrap;

import com.resolutech.domain.Category;
import com.resolutech.domain.Customer;
import com.resolutech.repositories.CategoryRepository;
import com.resolutech.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerBootstrap implements CommandLineRunner {

    private CustomerRepository customerRepository;

    public CustomerBootstrap(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        customerRepository.save(Customer.builder().firstname("Joe").lastname("Shmoe").build());
        customerRepository.save(Customer.builder().firstname("Bob").lastname("Black").build());
        customerRepository.save(Customer.builder().firstname("Ziggi").lastname("White").build());
        customerRepository.save(Customer.builder().firstname("Tard").lastname("Red").build());
        customerRepository.save(Customer.builder().firstname("Mott").lastname("Clam").build());

        log.debug("Customer loaded: " + customerRepository.count());
    }
}