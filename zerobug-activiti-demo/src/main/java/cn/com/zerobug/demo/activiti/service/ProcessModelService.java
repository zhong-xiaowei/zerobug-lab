package cn.com.zerobug.demo.activiti.service;

import cn.com.zerobug.demo.activiti.vo.response.ProcessModelResponseVo;
import cn.com.zerobug.demo.activiti.vo.query.ProcessModelQueryVo;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/1
 */
public interface ProcessModelService {

    /**
     * 查询模型列表
     *
     * @param processModelQueryVo 流程模型查询 VO
     * @return {@link ProcessModelResponseVo}
     */
    List<ProcessModelResponseVo> queryModelList(ProcessModelQueryVo processModelQueryVo);

    /**
     * 保存模型
     *
     * @param processModelResponseVo 流程模型
     * @return boolean
     */
    boolean saveModel(ProcessModelResponseVo processModelResponseVo);

    /**
     * 通过id 获取模型
     *
     * @param modelId 模型id
     * @return {@link ProcessModelResponseVo}
     */
    ProcessModelResponseVo getById(String modelId);

}
