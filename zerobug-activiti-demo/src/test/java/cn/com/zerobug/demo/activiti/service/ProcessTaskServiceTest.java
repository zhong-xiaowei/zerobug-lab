package cn.com.zerobug.demo.activiti.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProcessTaskServiceTest {

    @Autowired
    private ProcessTaskService processTaskService;

    @Test
    void queryTodoList() {
        processTaskService.queryTodoList();
    }
}