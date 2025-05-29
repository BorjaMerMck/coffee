package com.gammatech.coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase principal de la aplicación Coffee.
 * Esta clase contiene el punto de entrada principal de la aplicación Spring Boot.
 * 
 * La anotación @SpringBootApplication combina las siguientes anotaciones:
 * - @Configuration: Indica que la clase puede contener beans de configuración
 * - @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot
 * - @ComponentScan: Habilita el escaneo de componentes en el paquete actual
 */
@SpringBootApplication
@EntityScan("com.gammatech.coffee.models")
@EnableJpaRepositories("com.gammatech.coffee.repository")
public class CoffeeApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     * 
     * @param args Argumentos de línea de comandos pasados a la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(CoffeeApplication.class, args);
    }

}
