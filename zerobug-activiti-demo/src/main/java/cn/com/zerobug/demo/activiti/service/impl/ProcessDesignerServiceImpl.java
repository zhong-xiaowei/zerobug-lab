package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.extend.cmd.SaveModelDesignerCommand;
import cn.com.zerobug.demo.activiti.service.ProcessDesignerService;
import cn.com.zerobug.demo.activiti.vo.request.ProcessModelSaveRequestVo;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
@Service
public class ProcessDesignerServiceImpl implements ProcessDesignerService {

    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RepositoryService repositoryService;

    @Override
    public void saveModelXml(ProcessModelSaveRequestVo processModelSaveRequestVo) {
        managementService.executeCommand(new SaveModelDesignerCommand(processModelSaveRequestVo.getId(), processModelSaveRequestVo.getXmlStr()));
    }

    @Override
    public String readXml(String modelId) {
        byte[] editorBytes = repositoryService.getModelEditorSource(modelId);
        return new String(editorBytes, StandardCharsets.UTF_8);
    }
}
