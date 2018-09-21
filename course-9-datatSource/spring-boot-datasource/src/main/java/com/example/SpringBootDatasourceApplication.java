package com.example;

import com.example.dataSource.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan("com.example.mapper")
@Import({DynamicDataSourceConfig.class})
public class SpringBootDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDatasourceApplication.class, args);
    }
}
