package cn.com.zerobug.demo.activiti.config;

import cn.hutool.core.util.IdUtil;
import org.activiti.engine.impl.cfg.IdGenerator;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/9/12
 */
public class SnowflakeIdGenerator implements IdGenerator {

    @Override
    public String getNextId() {
        return IdUtil.getSnowflake().nextIdStr();
    }


}
