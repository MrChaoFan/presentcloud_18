package com.cyquen.presentcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Zhengxikun
 */
@EnableCaching
@SpringBootApplication
public class PresentCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentCloudApplication.class, args);
    }

}
