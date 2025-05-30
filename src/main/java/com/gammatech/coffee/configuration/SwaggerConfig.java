package com.gammatech.coffee.configuration;

public class SwaggerConfig {

    @Bean
    public OpenAPI cafeteriaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Cafetería")
                        .description("Sistema de gestión para cafetería que permite administrar cafés, clientes y pedidos")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sandra Amorós Reyes")
                                .email("nasdrahlovejinx@hotmail.com")));
    }
}
