package com.diaspogift.identityandaccess.port.adapter.resources.docs;

//@Configuration
//@EnableSwagger2
public class swagger2RestApiDocsConfig {

/*

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
                "Spring Boot REST API",
                "Spring Boot REST API for Online Store",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("", "", ""),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());

        return apiInfo;

    }


    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/anyPath.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("write", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("AUTHORIZATION", authorizationScopes));
    }

*/

}
