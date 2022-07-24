package cn.com.zerobug.demo.activiti.service.impl;

import cn.com.zerobug.demo.activiti.extend.cmd.SaveModelDesignerCommand;
import cn.com.zerobug.demo.activiti.service.ProcessDesignerService;
import cn.com.zerobug.demo.activiti.vo.ProcessModelSaveVo;
import org.activiti.engine.ManagementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
@Service
public class ProcessDesignerServiceImpl implements ProcessDesignerService {

    @Resource
    protected ManagementService managementService;

    @Override
    public void saveModelXml(ProcessModelSaveVo processModelSaveVo) {
        managementService.executeCommand(new SaveModelDesignerCommand(processModelSaveVo.getId(), processModelSaveVo.getXmlStr()));
    }
}
