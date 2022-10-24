package cn.com.zerobug.demo.activiti.utils;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
public class BpmnModelUtil {

    /**
     * 创建bpmn模型
     *
     * @return {@link BpmnModel}
     */
    public static BpmnModel createBpmnModel() {
        return new BpmnModel();
    }

    /**
     * 创建流程
     *
     * @param processId   流程id
     * @param processName 流程名称
     * @return {@link Process}
     */
    public static Process createProcess(String processId, String processName) {
        Process process = new Process();
        process.setId(processId);
        process.setName(processName);
        return process;
    }

    /**
     * 创建开始事件
     *
     * @return {@link StartEvent}
     */
    public static StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        startEvent.setName("startEvent");
        return startEvent;
    }

    /**
     * 创建结束事件
     *
     * @return {@link EndEvent}
     */
    public static EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        endEvent.setName("endEvent");
        return endEvent;
    }

    /**
     * 创建用户任务
     *
     * @param id              用户任务id
     * @param name            用户任务名
     * @param assignee        任务接收人
     * @param candidateUsers  候选人用户
     * @param candidateGroups 候选人组
     * @return {@link UserTask}
     */
    public static UserTask createUserTask(String id, String name, String assignee, String candidateUsers, String candidateGroups) {
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        userTask.setAssignee(assignee);
        if (StringUtils.hasLength(candidateUsers)) {
            userTask.setCandidateUsers(Arrays.asList(candidateUsers.split(",")));
        }
        if (StringUtils.hasLength(candidateGroups)) {
            userTask.setCandidateUsers(Arrays.asList(candidateGroups.split(",")));
        }
        return userTask;
    }


    /**
     * 创建链接
     *
     * @param from                源头
     * @param to                  目标
     * @param name                名字
     * @param conditionExpression 条件表达式
     * @return {@link SequenceFlow}
     */
    public static SequenceFlow link(FlowNode from, FlowNode to, String name, String conditionExpression) {
        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setSourceRef(from.getId());
        sequenceFlow.setTargetRef(to.getId());
        sequenceFlow.setName(name);
        if (StringUtils.hasLength(conditionExpression)) {
            sequenceFlow.setConditionExpression(conditionExpression);
        }
        return sequenceFlow;
    }
}
