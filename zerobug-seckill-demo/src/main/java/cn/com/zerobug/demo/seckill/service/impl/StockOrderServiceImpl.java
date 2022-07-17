package cn.com.zerobug.demo.seckill.service.impl;

import cn.com.zerobug.demo.seckill.entity.Stock;
import cn.com.zerobug.demo.seckill.service.StockService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.com.zerobug.demo.seckill.entity.StockOrder;
import cn.com.zerobug.demo.seckill.mapper.StockOrderMapper;
import cn.com.zerobug.demo.seckill.service.StockOrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/1
 */
@Slf4j
@Service
public class StockOrderServiceImpl extends ServiceImpl<StockOrderMapper, StockOrder> implements StockOrderService {

    @Autowired
    private StockService   stockService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean createOrder0(int sid) throws InterruptedException {
        Stock   stock;
        boolean updated = false;
        do {
            Thread.sleep(50);
            //校验库存
            stock = checkStock(sid);
            Integer old = stock.getSale();
            stock.setSale(old + 1);
            //扣库存
            updated = stockService.update(
                    stock, Wrappers.<Stock>lambdaUpdate()
                            .eq(Stock::getSale, old)
            );
        } while (!updated);
        //创建订单
        return createOrder(stock);
    }

    @Override
    public boolean createOrder1(int sid) throws InterruptedException {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("order:" + sid);
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1L, RateIntervalUnit.SECONDS);
        boolean b = rateLimiter.tryAcquire();
        if (b) {
            Stock   stock;
            boolean updated = false;
            do {
                Thread.sleep(50);
                //校验库存
                stock = checkStock(sid);
                Integer old = stock.getSale();
                stock.setSale(old + 1);
                //扣库存
                updated = stockService.update(
                        stock, Wrappers.<Stock>lambdaUpdate()
                                .eq(Stock::getSale, old)
                );
            } while (!updated);
            //创建订单
            return createOrder(stock);
        }
        return false;
    }

    private Stock checkStock(int sid) {
        Stock stock = stockService.getById(sid);
        log.info("当前库存:[{}]", stock.getCount() - stock.getSale());
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        }
        return stock;
    }

    private boolean createOrder(Stock stock) {
        StockOrder order = new StockOrder();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        return save(order);
    }

}
