package cn.com.zerobug.demo.activiti.controller;

 import cn.com.zerobug.demo.activiti.service.ProcessTaskService;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/25
 */
@RestController
@RequestMapping("/process/task")
public class ProcessTaskController {

    @Autowired
    private ProcessTaskService processTaskService;

    @GetMapping(value = "/queryTodoList")
    public void queryTodoList() {
        processTaskService.queryTodoList();
    }

    @PostMapping(value = "/complete")
    public void complete() {
        processTaskService.complete();
    }

    @PostMapping(value = "/reject")
    public void reject() {
        processTaskService.reject();
    }

}
