package cn.com.zerobug.demo.activiti.service;

import cn.com.zerobug.demo.activiti.vo.ProcessModelSaveReqVo;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
public interface ProcessDesignerService {

    void saveModelXml(ProcessModelSaveReqVo processModelSaveReqVo);

    String readXml(String modelId);
}
