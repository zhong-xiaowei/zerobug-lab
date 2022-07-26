package cn.com.zerobug.demo.excel.controller;

import cn.com.zerobug.demo.excel.ExceExportDemo;
import cn.com.zerobug.demo.excel.ExcelDemo;
import cn.com.zerobug.demo.excel.utils.CommonExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/25
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public void test(HttpServletResponse response) {
        ExcelDemo excelDemo = new ExcelDemo();
        excelDemo.setName("jack");
        excelDemo.setPhone("123123");
        excelDemo.setSex("男");
        excelDemo.setDate(new Date());

        ExcelDemo excelDemo1 = new ExcelDemo();
        excelDemo1.setName("jack");
        excelDemo1.setPhone("123123");
        excelDemo1.setSex("男");
        excelDemo1.setDate(new Date());

        ExcelDemo excelDemo2 = new ExcelDemo();
        excelDemo2.setName("jack");
        excelDemo2.setPhone("123123");
        excelDemo2.setSex("男");
        excelDemo2.setDate(new Date());

        excelDemo.setDemos(List.of(excelDemo1, excelDemo2));
        ExceExportDemo exceExportDemo = new ExceExportDemo();
        exceExportDemo.records.add(excelDemo);

        CommonExcelUtil.writeFileToResponse(exceExportDemo, response);
    }

}
