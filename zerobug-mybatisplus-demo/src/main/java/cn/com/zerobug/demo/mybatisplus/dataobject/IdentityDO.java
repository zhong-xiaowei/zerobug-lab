package cn.com.zerobug.demo.mybatisplus.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author zhongxiaowei
 * @date 2022/3/12
 */
@Data
@ToString
public class IdentityDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

}
