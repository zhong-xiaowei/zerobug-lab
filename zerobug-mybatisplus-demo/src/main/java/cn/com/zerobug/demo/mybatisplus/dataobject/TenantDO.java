package cn.com.zerobug.demo.mybatisplus.dataobject;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhongxiaowei
 * @date 2022/3/12
 */
@Data
@ToString(callSuper = true)
public class TenantDO extends BaseDO {

    /**
     * 租户id
     */
    private Long tenantId;
}
