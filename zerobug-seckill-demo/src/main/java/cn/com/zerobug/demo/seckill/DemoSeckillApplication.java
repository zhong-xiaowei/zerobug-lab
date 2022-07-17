package cn.com.zerobug.demo.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "cool.zyc.demo.seckill.mapper")
public class DemoSeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSeckillApplication.class, args);
    }

}
