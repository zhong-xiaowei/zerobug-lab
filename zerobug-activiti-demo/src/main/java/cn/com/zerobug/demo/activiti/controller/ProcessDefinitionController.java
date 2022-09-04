package cn.com.zerobug.demo.activiti.controller;

import cn.com.zerobug.demo.activiti.service.ProcessDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
@RestController
@RequestMapping("/process/definition")
public class ProcessDefinitionController {

    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @GetMapping("/readXml/{deployId}")
    public String readXml(@PathVariable(value = "deployId") String deployId) {
        return processDefinitionService.readXml(deployId);
    }

}
