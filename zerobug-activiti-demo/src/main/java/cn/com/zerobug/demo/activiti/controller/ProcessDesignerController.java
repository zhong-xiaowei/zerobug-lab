package cn.com.zerobug.demo.activiti.controller;

import cn.com.zerobug.demo.activiti.service.ProcessDesignerService;
import cn.com.zerobug.demo.activiti.vo.ProcessModelSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
@RestController
@RequestMapping("/process/designer")
public class ProcessDesignerController {

    @Resource
    private ProcessDesignerService processDesignerService;

    @GetMapping("/test")
    public boolean test() {
        return true;
    }

    @PostMapping("/save")
    public boolean save(@RequestBody ProcessModelSaveVo processModelSaveVo) {
        processDesignerService.saveModelXml(processModelSaveVo);
        return true;
    }

}
