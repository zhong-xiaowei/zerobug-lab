package cn.com.zerobug.demo.mybatisplus.dataobject;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhongxiaowei
 * @date 2022/3/12
 */
@Data
@ToString(callSuper = true)
public class BaseDO extends IdentityDO {

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 逻辑删除 0-未删除 1-删除
     */
    private Integer isDelete;

}
