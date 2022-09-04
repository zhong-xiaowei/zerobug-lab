package cn.com.zerobug.demo.activiti.service;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
public interface ProcessDefinitionService {

    /**
     * 读取xml
     *
     * @param deployId 部署id
     * @return {@link String}
     */
    String readXml(String deployId);

}
