package dev.jakubw.conifg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .servers(List.of(new Server().url("/")))
                .info(new Info()
                        .title("Advertise service - API")
                        .description("api for ad service")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Java Developer")
                                .email("dev@example.com")
                        ).license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi providerGroup(){
        return GroupedOpenApi.builder()
                .group("provider")
                .pathsToMatch("/api/ad/provider/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adGroup(){
        return GroupedOpenApi.builder()
                .group("ads")
                .pathsToMatch("/api/ad/ads/**")
                .build();
    }

    @Bean
    public GroupedOpenApi impressionGroup(){
        return GroupedOpenApi.builder()
                .group("impression")
                .pathsToMatch("/api/ad/impression/**")
                .build();
    }
}
