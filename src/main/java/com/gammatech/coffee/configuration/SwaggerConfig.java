package com.gammatech.coffee.configuration;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {

    @Bean
    public OpenAPI cafeteriaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Cafetería")
                        .description("Sistema de gestión para cafetería que permite administrar cafés, clientes y pedidos")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Borja Merchan Mckenna")
                                .email("borjamerchan146@gmail.com")));
    }
}
