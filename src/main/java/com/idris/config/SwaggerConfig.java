package com.idris.config;

import com.google.common.base.Predicate;
import com.idris.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(getApiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex(Constants.BASE_URL+".*"));
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("IDRIS USER ACCOUNT API")
                .description("The service manages POST, POST, DELETE AND GET for user accounts")
                .version("1.0")
                .contact(new Contact("IDRIS", "", "idrisidah@gmail.com"))
                .build();
    }


}