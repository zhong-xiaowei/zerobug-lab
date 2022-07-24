package cn.com.zerobug.demo.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author zhongxiaowei
 */
@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class ActivitiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiDemoApplication.class, args);
    }

}
