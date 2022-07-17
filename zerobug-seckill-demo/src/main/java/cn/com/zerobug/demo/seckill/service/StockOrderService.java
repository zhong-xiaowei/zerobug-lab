package cn.com.zerobug.demo.seckill.service;

import cn.com.zerobug.demo.seckill.entity.StockOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/1
 */
public interface StockOrderService extends IService<StockOrder>{

    boolean createOrder0(int sid) throws InterruptedException;

    boolean createOrder1(int sid) throws InterruptedException;

}
