package com.example.cache.caffeine_cache.component;

import com.example.cache.caffeine_cache.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CaffeineCache implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CaffeineCache.class);

    @Autowired
    public ExampleService exampleService;

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Caching Started");
        System.out.println(exampleService.getString());
        System.out.println(exampleService.getString());
        System.out.println(exampleService.getString());

    }
}
