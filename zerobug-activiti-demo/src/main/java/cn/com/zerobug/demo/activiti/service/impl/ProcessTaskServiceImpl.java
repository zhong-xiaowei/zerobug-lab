package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.service.ProcessTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/25
 */
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private TaskService taskService;

    @Override
    public void queryTodoList() {
        Long userId = 1L;
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.active().includeProcessVariables()
                .or()
                .taskCandidateOrAssigned(userId.toString())
                .taskOwner(userId.toString())
                .endOr()
                .orderByTaskCreateTime().desc();
        List<Task> taskList = taskQuery.listPage(0,10);
        System.out.println(taskList);
    }

    @Override
    public void complete() {

    }

    @Override
    public void reject() {

    }
}
