package com.example.shopbackend.shopcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ShopCartApplication is the main entry point for the ShopCart backend application.
 * This Spring Boot application provides RESTful APIs for a full-stack e-commerce platform,
 * allowing users to register, login, search products, manage cart, checkout, and process payments.
 * It follows a Controller-Service-DAO layered architecture and implements common design patterns
 * like Singleton, DAO, and MVC to ensure clean, maintainable, and scalable code.

 *  @version shopcart_1.0.0.1
 *  @author ErAmruta Kumbhar
 *
 * Swagger documentation is enabled to provide interactive API documentation.
 *  * Swagger (OpenAPI 3) documentation will be auto-configured by Springdoc.
 */
@SpringBootApplication
public class ShopcartApplication {

    /**
     * Main method to launch the Spring Boot application.
     * This will start the embedded Tomcat server, initialize all Spring Beans,
     * scan for components (Controllers, Services, Repositories), and expose REST APIs.
     * @param args command-line arguments (not used if in future for test date wise product  we can test)
     */
    public static void main(String[] args) {
        SpringApplication.run(ShopcartApplication.class, args);
    }

}
