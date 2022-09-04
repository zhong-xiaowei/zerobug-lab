package cn.com.zerobug.demo.openapi.controller;

import com.dd.component.excel.annotation.ExcelExport;
import com.dd.component.excel.annotation.ExcelImport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/excel")
    @ExcelExport(clazz = TestEntity.class, fileName = "测试文件")
    public List<TestEntity> testExcel(String name, HttpServletResponse response) {
        TestEntity testEntity = new TestEntity();
        testEntity.setUserName("zhangsan");
        testEntity.setAge(23);
        testEntity.setIntValue(2);
        testEntity.setGender("男");
        testEntity.setBirthday(new Date());
        testEntity.setMoney(115555.22);
        testEntity.setMoney2(BigDecimal.valueOf(115555.22));

        TestEntity testEntity1 = new TestEntity();
        testEntity1.setUserName("lisi");
        testEntity1.setIntValue(1);
        testEntity1.setAge(25);
        testEntity1.setGender("男");
        testEntity1.setBirthday(new Date());
        testEntity1.setMoney(11.3);
        testEntity1.setMoney2(BigDecimal.valueOf(1155551.22));
        return List.of(testEntity, testEntity1);
//        return List.of();
    }

    @PostMapping("/import")
    public void importData(@ExcelImport List<TestEntity> entities) {
        for (TestEntity entity : entities) {
            System.out.println(entity);
        }
    }

//    @PostMapping("/{channel}/{type}")
//    public String testAli(@PathVariable("channel") Integer channel, @PathVariable("type") Integer type, @RequestParam Map<String, String> params, @RequestBody String body) {
//        System.out.println(channel);
//        System.out.println(type);
//        System.out.println(params);
//        PayAsyncNotifyDto payAsyncNotifyDto = new PayAsyncNotifyDto();
//        payAsyncNotifyDto.setBody(body);
//        payAsyncNotifyDto.setParamsMap(params);
//        PayResult<AlipayNotifyResponseDto> asyncNotify = PayClientFactory.getPayClient(PaymentChannelEnum.ALIPAY_PC).asyncNotify(payAsyncNotifyDto);
//        return "SUCCESS";
//    }

}
