package cn.com.zerobug.demo.activiti.extend.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.Model;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
public class SaveModelDesignerCommand implements Command<Void>, Serializable {

    private static final long   serialVersionUID = -8246376454910405400L;
    private static final Logger LOGGER           = LoggerFactory.getLogger(SaveModelDesignerCommand.class);

    private String modelId;
    private String key;
    private String name;
    private String modelXml;
    private String category;
    private String description;
    private String tenantId;

    public SaveModelDesignerCommand(String modelId, String modelXml) {
        this(modelId, null, null, modelXml, null, null, null);
    }

    public SaveModelDesignerCommand(String modelId, String key, String name, String modelXml, String category, String description, String tenantId) {
        this.modelId = modelId;
        this.key = key;
        this.name = name;
        this.modelXml = modelXml;
        this.category = category;
        this.description = description;
        this.tenantId = tenantId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
        RepositoryService              repositoryService          = processEngineConfiguration.getRepositoryService();
        if (Objects.isNull(modelId) && Objects.isNull(modelXml)) {
            throw new ActivitiException("modelId 和 modelXml 都为空!");
        }
        try {
            BpmnModel        bpmnModel        = null;
            byte[]           modelXmlBytes    = null;
            XMLInputFactory  xmlInputFactory  = XMLInputFactory.newInstance();
            BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
            if (StringUtils.hasLength(modelXml)) {
                modelXmlBytes = modelXml.getBytes(StandardCharsets.UTF_8);
                InputStreamReader xmlIsr = new InputStreamReader(new ByteArrayInputStream(modelXmlBytes));
                XMLStreamReader   xmlSr  = xmlInputFactory.createXMLStreamReader(xmlIsr);
                bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSr);
                settingProperties(bpmnModel);
            } else {
                byte[] oldBytes = repositoryService.getModelEditorSource(modelId);
                if (oldBytes != null) {
                    InputStreamReader xmlIn = new InputStreamReader(new ByteArrayInputStream(oldBytes), StandardCharsets.UTF_8);
                    XMLStreamReader   xmlSr = xmlInputFactory.createXMLStreamReader(xmlIn);
                    bpmnModel = bpmnXMLConverter.convertToBpmnModel(xmlSr);
                    bpmnModel.setTargetNamespace(category);
                    bpmnModel.getMainProcess().setId(key);
                    bpmnModel.getMainProcess().setName(name);
                    bpmnModel.getMainProcess().setDocumentation(description);
                    modelXmlBytes = bpmnXMLConverter.convertToXML(bpmnModel, StandardCharsets.UTF_8.name());
                }
            }
            Model model = updateModel(repositoryService);
            if (!Objects.isNull(modelXmlBytes)) {
                generateFlowImages(processEngineConfiguration, repositoryService,
                        bpmnModel, modelXmlBytes, model);
            }
        } catch (XMLStreamException e) {
            LOGGER.error("读取XML失败", e);
            throw new ActivitiException("SaveModelDesignerCommand 执行错误, 读取XML失败..", e);
        }
        return null;
    }

    private Model updateModel(RepositoryService repositoryService) {
        Model model = null;
        if (Objects.isNull(modelId)) {
            model = repositoryService.newModel();
            model.setTenantId(Objects.isNull(tenantId) ? null : tenantId);
        } else {
            model = repositoryService.getModel(modelId);
            model.setVersion(model.getVersion() + 1);
        }
        if (!key.equals(model.getKey())) {
            long count = repositoryService.createModelQuery().modelKey(key).count();
            if (count > 0) {
                throw new ActivitiObjectNotFoundException(key + " 已存在");
            }
        }

        Optional.ofNullable(repositoryService.createModelQuery().modelKey(key).list())
                .orElse(Collections.emptyList()).stream().filter(f -> !f.getId().equals(modelId))
                .forEach(m -> {
                    m.setKey(key);
                    m.setName(name);
                    m.setCategory(category);
                    repositoryService.saveModel(m);
                });

        model.setKey(key);
        model.setName(name);
        model.setCategory(category);
        model.setMetaInfo(getMetaInfo(name, description));
        repositoryService.saveModel(model);
        return model;
    }

    private void generateFlowImages(ProcessEngineConfigurationImpl processEngineConfiguration, RepositoryService repositoryService, BpmnModel bpmnModel, byte[] modelXmlBytes, Model model) {
        repositoryService.addModelEditorSource(model.getId(), modelXmlBytes);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png",
                Collections.emptyList(), Collections.emptyList(),
                processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(),
                processEngineConfiguration.getAnnotationFontName(),
                processEngineConfiguration.getClassLoader(),
                1.0);
        repositoryService.addModelEditorSourceExtra(model.getId(), IoUtil.readInputStream(resource, "diagramGenerator.generateDiagram"));
    }

    private void settingProperties(BpmnModel bpmnModel) {
        key = bpmnModel.getMainProcess().getId();
        name = bpmnModel.getMainProcess().getName();
        category = bpmnModel.getTargetNamespace();
        description = bpmnModel.getMainProcess().getDocumentation();
    }

    protected String getMetaInfo(String name, String description) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode   metaInfo     = objectMapper.createObjectNode();
        metaInfo.put("name", name);
        metaInfo.put("description", description);
        return metaInfo.toString();
    }
}
