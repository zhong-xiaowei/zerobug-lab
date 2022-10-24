package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.service.ProcessModelService;
import cn.com.zerobug.demo.activiti.utils.JacksonUtil;
import cn.com.zerobug.demo.activiti.vo.response.ProcessModelResponseVo;
import cn.com.zerobug.demo.activiti.vo.query.ProcessModelQueryVo;
import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
@Service
public class ProcessModelServiceImpl implements ProcessModelService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public List<ProcessModelResponseVo> queryModelList(ProcessModelQueryVo processModelQueryVo) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        buildQueryParams(processModelQueryVo, modelQuery);
        // TODO 暂时写死
        List<Model> models = modelQuery.listPage(0, 10);
        return models.stream().map(model -> {
            ProcessModelResponseVo response = new ProcessModelResponseVo();
            BeanUtils.copyProperties(model, response);
            try {
                JsonNode modelNode = JacksonUtil.getObjectMapper().readTree(model.getMetaInfo());
                response.setDescription(modelNode.get("description").asText());
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            response.setDeployed(model.getDeploymentId() != null ? true : false);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean saveModel(ProcessModelResponseVo processModelResponseVo) {
        long countNum = repositoryService.createModelQuery().modelKey(processModelResponseVo.getKey()).count();
        Assert.isTrue(countNum == 0, "模型已存在");
        String metaInfo = getMetaInfo(processModelResponseVo);
        Model model = repositoryService.newModel();
        model.setKey(processModelResponseVo.getKey());
        model.setName(processModelResponseVo.getName());
        model.setVersion(1);
        model.setMetaInfo(metaInfo);
        model.setTenantId(processModelResponseVo.getTenantId());
        model.setCategory(processModelResponseVo.getCategory());
        repositoryService.saveModel(model);
        return true;
    }

    @Override
    public ProcessModelResponseVo getById(String modelId) {
        Model model = repositoryService.getModel(modelId);
        ProcessModelResponseVo processModelResponseVo = new ProcessModelResponseVo();
        BeanUtils.copyProperties(model, processModelResponseVo);
        return processModelResponseVo;
    }

    private String getMetaInfo(ProcessModelResponseVo processModelResponseVo) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name", processModelResponseVo.getName());
        metaInfo.put("description", processModelResponseVo.getDescription());
        return metaInfo.toString();
    }


    private static void buildQueryParams(ProcessModelQueryVo processModelQueryVo, ModelQuery modelQuery) {
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getModelId())) {
            modelQuery.modelId(processModelQueryVo.getModelId());
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getModelCategory())) {
            modelQuery.modelCategoryLike("%" + processModelQueryVo.getModelCategory() + "%");
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getModelName())) {
            modelQuery.modelNameLike(processModelQueryVo.getModelName());
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getModelKey())) {
            modelQuery.modelKey(processModelQueryVo.getModelKey());
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getModelVersion())) {
            modelQuery.modelVersion(processModelQueryVo.getModelVersion());
        }
        if (processModelQueryVo.getLatestVersion() != null && processModelQueryVo.getLatestVersion()) {
            modelQuery.latestVersion();
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getDeploymentId())) {
            modelQuery.deploymentId(processModelQueryVo.getDeploymentId());
        }
        if (processModelQueryVo.getDeployed() != null) {
            if (processModelQueryVo.getDeployed()) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }
        if (ObjectUtils.isNotEmpty(processModelQueryVo.getTenantId())) {
            modelQuery.modelTenantId(processModelQueryVo.getTenantId());
        }
    }

}
