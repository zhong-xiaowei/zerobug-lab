package cn.com.zerobug.demo.activiti.service;

import cn.com.zerobug.demo.activiti.vo.ProcessModelSaveVo;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/24
 */
public interface ProcessDesignerService {

    /**
     * 保存模型 XML
     * @param processModelSaveVo
     */
    void saveModelXml(ProcessModelSaveVo processModelSaveVo);

}
