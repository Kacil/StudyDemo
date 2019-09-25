package com.zxk.demoserviceuser;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableEurekaClient
public class DemoServiceUserApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceUserApplication.class, args);
    }

    @Bean
    public DataSource getDataSource() throws Exception {
        String dataBaseName = env.getProperty("mysql.dataBaseName");
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.url")+"/"+dataBaseName+"?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
        props.put("username", env.getProperty("spring.datasource.username"));
        props.put("password", env.getProperty("spring.datasource.password"));
        return DruidDataSourceFactory.createDataSource(props);

    }

}
