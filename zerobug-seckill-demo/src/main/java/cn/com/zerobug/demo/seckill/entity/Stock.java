package cn.com.zerobug.demo.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/1
 */
@Data
public class Stock {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String  name;
    private Integer count;
    private Integer sale;
    private Integer version;

}
