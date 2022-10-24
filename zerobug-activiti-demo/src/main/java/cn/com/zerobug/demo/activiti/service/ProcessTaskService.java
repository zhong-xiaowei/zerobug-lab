package cn.com.zerobug.demo.activiti.service;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/25
 */
public interface ProcessTaskService {

    void queryTodoList();

    void complete();

    void reject();
}
