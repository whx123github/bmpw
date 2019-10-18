package com.jt.baimo.pw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加对swagger的支持
 * <p>
 * Created by yb_li on 2019/1/5.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(getGlobalParams());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("百陌跨城")
                .version("1.0").build();
    }


    private List<Parameter> getGlobalParams() {
        ParameterBuilder pd = new ParameterBuilder();
        pd.parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("token").defaultValue(
                        "eyJhbGciOiJIUzI1NiJ9.eyJyYW5kb21LZXkiOiI5NzE0NDkiLCJpYXQiOjE1NzEwMzE5NTcsImp0aSI6ImZlMjllOWU3MDM3ZTdmY2QzMzRmODI2ODkyNmZkYTU1In0.mKeWQ6eD_4rAKQUuXeYrTixRAO8X7Y24rFicbevLsA8")
                .modelRef(new ModelRef("string"));
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(pd.build());
        return aParameters;
    }
}
