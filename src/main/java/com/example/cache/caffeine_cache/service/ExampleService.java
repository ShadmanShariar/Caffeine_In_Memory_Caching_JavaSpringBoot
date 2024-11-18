package com.example.cache.caffeine_cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "example")
public class ExampleService {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleService.class);

    @Cacheable
    public String getString(){
        LOG.info("Called");
        return "String Called";
    }

}
