package com.zxk.demoredisserver.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Primary
public class Swagger2 {
    //是否开启swagger，正式环境一般是需要关闭的，可根据多环境配置进行设置
    @Value(value = "${swagger.enabled}")
    Boolean eanbled;
    @Value(value = "${swagger.title}")
    String title;
    @Value(value = "${swagger.description}")
    String description;
    @Value(value = "${swagger.contact.name}")
    String contactName;
    @Value(value = "${swagger.contact.url}")
    String contactUrl;
    @Value(value = "${swagger.contact.email}")
    String contactEmail;
    @Value(value = "${swagger.version}")
    String version;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(eanbled).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.zxk.demoredisserver.service"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    //设置api信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }
}
