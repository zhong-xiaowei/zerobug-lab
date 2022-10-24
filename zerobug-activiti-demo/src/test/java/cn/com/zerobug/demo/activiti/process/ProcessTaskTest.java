package cn.com.zerobug.demo.activiti.process;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
@SpringBootTest
public class ProcessTaskTest {

    @Test
    public void startProcessTest() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        defaultProcessEngine.getRuntimeService().startProcessInstanceByKey("test_1");
    }

    @Test
    public void queryTodoListTest() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskQuery taskQuery = defaultProcessEngine.getTaskService().createTaskQuery()
                .active()
                .includeTaskLocalVariables()
                .or()
                .taskCandidateOrAssigned("zhangsan")
                .endOr();
        List<Task> list = taskQuery.list();
        System.out.println(list);
    }

    @Test
    public void claim(){

    }

}
