package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.service.ProcessDefinitionService;
import cn.hutool.core.io.IoUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    @Autowired
    protected RepositoryService repositoryService;

    @Override
    public String readXml(String deployId) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        Deployment deployment = deploymentQuery.deploymentId(deployId).singleResult();
        InputStream resource = repositoryService.getResourceAsStream(deployment.getId(), deployment.getName());
        return IoUtil.read(resource, StandardCharsets.UTF_8);
    }
}
