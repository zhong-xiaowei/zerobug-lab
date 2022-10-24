package cn.com.zerobug.demo.activiti.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void selectIdByUserId() {
        List<Integer> integers = roleMapper.selectIdByUserId(2);
        System.out.println(integers);
    }
}