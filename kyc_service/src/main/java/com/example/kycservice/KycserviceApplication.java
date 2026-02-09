package com.example.kycservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * kyc_service Spring Boot entrypoint.
 *
 * Scans base package com.example.kycservice.* for components, controllers, entities, and repositories.
 */
@SpringBootApplication
public class KycserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KycserviceApplication.class, args);
    }
}
