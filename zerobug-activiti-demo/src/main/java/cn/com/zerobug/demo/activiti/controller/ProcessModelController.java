package cn.com.zerobug.demo.activiti.controller;

import cn.com.zerobug.demo.activiti.service.ProcessModelService;
import cn.com.zerobug.demo.activiti.vo.ProcessModelResVo;
import cn.com.zerobug.demo.activiti.vo.query.ProcessModelQueryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
@RestController
@RequestMapping("/process/model")
public class ProcessModelController {

    protected static final Logger logger = LoggerFactory.getLogger(ProcessModelController.class);

    @Autowired
    private ProcessModelService processModelService;

    @GetMapping("/list-page")
    public List<ProcessModelResVo> listPage(ProcessModelQueryVo model) {
        return processModelService.queryModelList(model);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody ProcessModelResVo processModelResVo) {
        return processModelService.saveModel(processModelResVo);
    }

}
