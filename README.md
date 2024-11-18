# Caffeine In-Memory Caching in Spring Boot
## Author - Shadman Shariar

This project demonstrates the integration of **Caffeine**, a high-performance in-memory caching library, with **Spring Boot** for efficient data caching.

## Features

- **Caffeine Cache Integration**: Leverages Caffeine for fast, in-memory caching.
- **Spring Boot Cache Abstraction**: Uses Spring's caching annotations for seamless cache management.
- **Custom Cache Configuration**: Fine-tuned cache settings like expiry, maximum size, etc.

## Prerequisites

- Java 11 or higher
- Maven or Gradle build tool
- Spring Boot version 2.5+ (recommended)

## Dependencies

Ensure the following dependencies are added to your build tool:

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
        <groupId>com.github.ben-manes.caffeine</groupId>
        <artifactId>caffeine</artifactId>
    </dependency>
</dependencies>
```

### Gradle
```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-cache'
    implementation 'com.github.ben-manes.caffeine:caffeine'
}
```

## Getting Started

### Step 1: Enable Caching in Spring Boot

Add the `@EnableCaching` annotation to your main application class:

```java
@SpringBootApplication
@EnableCaching
public class CaffeineCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineCacheApplication.class, args);
    }
}
```

### Step 2: Configure Caffeine Cache

Create a `CacheConfig` class to define the Caffeine cache settings:

```java
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
```

### Step 3: Add Caching to Methods

Use Spring's `@Cacheable`, `@CachePut`, and `@CacheEvict` annotations to define caching behavior.

Example:
```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Cacheable(value = "dataCache", key = "#id")
    public String getDataById(String id) {
        simulateSlowService();
        return "Data for ID: " + id;
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000); // Simulate a slow service
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
```

### Step 4: Testing the Cache

Call the `getDataById` method multiple times with the same `id`. The first call will take time as the data is being fetched, while subsequent calls will be significantly faster due to caching.

Example:
```java
@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/{id}")
    public String getData(@PathVariable String id) {
        return dataService.getDataById(id);
    }
}
```

### Step 5: Run the Application

Start the application and access the endpoint:

```
GET http://localhost:8080/data/{id}
```

Replace `{id}` with any value. Notice the difference in response times for subsequent requests with the same `id`.

## Customizing Cache Behavior

Modify `CacheConfig` to adjust cache properties:

- **Expire after access**:
  ```java
  .expireAfterAccess(10, TimeUnit.MINUTES)
  ```
- **Initial capacity and maximum size**:
  ```java
  .initialCapacity(50)
  .maximumSize(500)
  ```

## Contributing

Feel free to fork this repository and submit pull requests for improvements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

--- 

This `README.md` provides a complete guide to setting up Caffeine caching in a Spring Boot application.
