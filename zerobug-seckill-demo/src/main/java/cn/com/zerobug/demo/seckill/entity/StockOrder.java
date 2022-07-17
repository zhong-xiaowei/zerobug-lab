package cn.com.zerobug.demo.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/1
 */
@Data
public class StockOrder {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sid;
    private String  name;
    private Date    createTime;

}
