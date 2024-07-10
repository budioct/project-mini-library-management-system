package dev.budhi.latihan.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Library Application")
                        .version("1.0")
                        .description("API documentation for the Library Application")
                        .contact(new Contact()
                                .name("Budhi Octaviansyah")
                                .email("budioct@gmail.com")
                                .url("https://www.linkedin.com/in/budhi-octaviansyah/")));
    }

}
