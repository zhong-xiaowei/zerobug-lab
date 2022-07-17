package cn.com.zerobug.demo.seckill.service.impl;

import cn.com.zerobug.demo.seckill.entity.Stock;
import cn.com.zerobug.demo.seckill.service.StockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.com.zerobug.demo.seckill.mapper.StockMapper;
import org.springframework.stereotype.Service;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/2
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {
}
