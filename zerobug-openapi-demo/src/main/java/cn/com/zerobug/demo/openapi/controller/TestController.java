package cn.com.zerobug.demo.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/24
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "test";
    }

}
