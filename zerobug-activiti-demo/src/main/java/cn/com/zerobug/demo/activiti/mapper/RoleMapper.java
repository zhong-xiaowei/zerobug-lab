package cn.com.zerobug.demo.activiti.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/10/22
 */
@Mapper
public interface RoleMapper {

    @Select("SELECT r.role_id FROM sys_role r INNER JOIN sys_user_role ur ON r.role_id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Integer> selectIdByUserId(@Param("userId") Integer userId);

}
