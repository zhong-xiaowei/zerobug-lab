package cn.com.zerobug.demo.activiti.service;

import cn.com.zerobug.demo.activiti.vo.request.ProcessModelSaveRequestVo;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
public interface ProcessDesignerService {

    void saveModelXml(ProcessModelSaveRequestVo processModelSaveRequestVo);

    String readXml(String modelId);
}
