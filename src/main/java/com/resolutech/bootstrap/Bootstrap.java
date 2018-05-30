package com.resolutech.bootstrap;

import com.resolutech.domain.Category;
import com.resolutech.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryRepository.save(Category.builder().name("Zomg").build());
        categoryRepository.save(Category.builder().name("Fresh").build());
        categoryRepository.save(Category.builder().name("Zor").build());
        categoryRepository.save(Category.builder().name("Dried").build());
        categoryRepository.save(Category.builder().name("Spicy").build());
        categoryRepository.save(Category.builder().name("Smooth").build());

        log.debug("Category loaded: " + categoryRepository.count());
    }
}