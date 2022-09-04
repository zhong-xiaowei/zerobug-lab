package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.extend.cmd.SaveModelDesignerCommand;
import cn.com.zerobug.demo.activiti.service.ProcessDesignerService;
import cn.com.zerobug.demo.activiti.vo.ProcessModelSaveReqVo;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
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
    public void saveModelXml(ProcessModelSaveReqVo processModelSaveReqVo) {
        managementService.executeCommand(new SaveModelDesignerCommand(processModelSaveReqVo.getId(), processModelSaveReqVo.getXmlStr()));
    }

    @Override
    public String readXml(String modelId) {
        byte[] editorBytes = repositoryService.getModelEditorSource(modelId);
        return new String(editorBytes, StandardCharsets.UTF_8);
    }
}
