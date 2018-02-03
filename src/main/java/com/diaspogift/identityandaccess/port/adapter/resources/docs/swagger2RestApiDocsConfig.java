package com.diaspogift.identityandaccess.port.adapter.resources.docs;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class swagger2RestApiDocsConfig {


    @Bean
    public Docket resourcesApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.diaspogift.identityandaccess.port.adapter.resources"))
                .paths(regex("/api/v1/tenants.*"))
                .build()
                .apiInfo(metaData())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()));


    }

    private ApiInfo metaData() {

        ApiInfo apiInfo = new ApiInfo(
                "IDENTITY AND ACCESS",
                "Identity and Access REST API",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("", "", ""),
                "iam license",
                "https://www.diaspogift.com/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());

        return apiInfo;

    }


    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("write", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("AUTHORIZATION", authorizationScopes));
    }


}
