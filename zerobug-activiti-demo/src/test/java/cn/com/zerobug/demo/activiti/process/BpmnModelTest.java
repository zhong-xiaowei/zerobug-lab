package cn.com.zerobug.demo.activiti.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static cn.com.zerobug.demo.activiti.utils.BpmnModelUtil.*;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/11
 */
@SpringBootTest
public class BpmnModelTest {

    private RepositoryService repositoryService;
    private ProcessEngine processEngine;

    @BeforeEach
    public void setUp() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

    @Test
    public void processCodeGenerationTest() {
        BpmnModel bpmnModel = createBpmnModel();

        Process process = createProcess("test_1", "test_1_name");

        StartEvent startEvent = createStartEvent();
        process.addFlowElement(startEvent);
        UserTask userTask = createUserTask("task1", "审批1", "zhangsan", "", "");
        process.addFlowElement(userTask);
        UserTask userTask1 = createUserTask("task2", "审批2", "lisi", "", "");
        process.addFlowElement(userTask1);
        EndEvent endEvent = createEndEvent();
        process.addFlowElement(endEvent);

        process.addFlowElement(link(startEvent, userTask, "", ""));
        process.addFlowElement(link(userTask, userTask1, "", ""));
        process.addFlowElement(link(userTask1, endEvent, "", ""));

        bpmnModel.addProcess(process);

        String key = bpmnModel.getMainProcess().getId();
        String name = bpmnModel.getMainProcess().getName();
        String category = bpmnModel.getTargetNamespace();
        String description = bpmnModel.getMainProcess().getDocumentation();

        Model model = repositoryService.newModel();

        model.setKey(key);
        model.setName(name);
        model.setCategory(category);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name", name);
        metaInfo.put("description", description);
        model.setMetaInfo(metaInfo.toString());

        new BpmnAutoLayout(bpmnModel).execute();

        repositoryService.saveModel(model);
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] xmlBytes = bpmnXMLConverter.convertToXML(bpmnModel);
        repositoryService.addModelEditorSource(model.getId(), xmlBytes);
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream resource = diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                Collections.emptyList(), Collections.emptyList(),
                processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(),
                processEngineConfiguration.getAnnotationFontName(),
                processEngineConfiguration.getClassLoader(),
                1.0);
        repositoryService.addModelEditorSourceExtra(model.getId(), IoUtil.readInputStream(resource, "diagramGenerator.generateDiagram"));
    }


    @Test
    public void getProcessModelXmlTest() {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.modelKey("test_1");
        Model model = modelQuery.singleResult();
        byte[] modelEditorSource = repositoryService.getModelEditorSource(model.getId());
        String s = new String(modelEditorSource, StandardCharsets.UTF_8);
        System.out.println(s);
    }

    @Test
    public void deploymentProcessTest() throws XMLStreamException {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.modelKey("test_1");
        Model model = modelQuery.singleResult();
        byte[] modelEditorSource = repositoryService.getModelEditorSource(model.getId());
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        InputStreamReader xmlIsr = new InputStreamReader(new ByteArrayInputStream(modelEditorSource));
        XMLStreamReader xmlSr = xmlInputFactory.createXMLStreamReader(xmlIsr);
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSr);
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel(model.getName() + ".bpmn20.xml", bpmnModel)
                .name(model.getName())
                .key(model.getKey())
                .category(model.getCategory())
                .deploy();

        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

    @Test
    public void deploymentProcessByClassPathTest() throws XMLStreamException {
        repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .addClasspathResource("processes/请假流程.bpmn20.xml")
                .deploy();

        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }

}
