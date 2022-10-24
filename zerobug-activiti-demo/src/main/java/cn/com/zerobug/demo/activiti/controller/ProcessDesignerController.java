package cn.com.zerobug.demo.activiti.controller;

import cn.com.zerobug.demo.activiti.service.ProcessDesignerService;
import cn.com.zerobug.demo.activiti.vo.request.ProcessModelSaveRequestVo;
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

    @PostMapping("/save")
    public boolean save(@RequestBody ProcessModelSaveRequestVo processModelSaveRequestVo) {
        processDesignerService.saveModelXml(processModelSaveRequestVo);
        return true;
    }

    @GetMapping("/readXml/{modelId}")
    public String readXml(@PathVariable(value = "modelId") String modelId) {
        return processDesignerService.readXml(modelId);
    }

}
