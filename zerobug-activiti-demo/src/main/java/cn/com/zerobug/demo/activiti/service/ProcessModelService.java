package cn.com.zerobug.demo.activiti.service;

import cn.com.zerobug.demo.activiti.vo.ProcessModelResVo;
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
     * @return {@link ProcessModelResVo}
     */
    List<ProcessModelResVo> queryModelList(ProcessModelQueryVo processModelQueryVo);

    /**
     * 保存模型
     *
     * @param processModelResVo 流程模型
     * @return boolean
     */
    boolean saveModel(ProcessModelResVo processModelResVo);
}
