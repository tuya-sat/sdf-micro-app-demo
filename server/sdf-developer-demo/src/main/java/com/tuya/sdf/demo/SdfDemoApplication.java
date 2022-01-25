package com.tuya.sdf.demo;

import com.tuya.connector.spring.annotations.ConnectorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 哲也
 * @className SdfDemoApplication
 * @desc
 * @since 2021/11/19
 */
@SpringBootApplication(scanBasePackages = {"com.tuya.sdf.demo", "com.tuya.sdf.starter"})
@ConnectorScan(basePackages = {"com.tuya.sdf.**.connector"})
public class SdfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdfDemoApplication.class, args);
    }
}
