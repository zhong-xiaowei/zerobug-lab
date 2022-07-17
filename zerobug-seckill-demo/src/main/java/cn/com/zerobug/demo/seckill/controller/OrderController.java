package cn.com.zerobug.demo.seckill.controller;

import cn.com.zerobug.demo.seckill.service.StockOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/7/1
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private StockOrderService stockOrderService;

    /**
     * 数据库乐观锁秒杀
     * 问题：
     * 1:并发容易把数据库拉爆
     * 2:同步等待时间长，需要循环去做修改，系统资源严重占用
     * 3:性能很低很低
     *
     * @param sid
     * @return
     */
    @GetMapping("/seckill-0/{sid}")
    public String seckill0(@PathVariable int sid) {
        boolean success = false;
        try {
            success = stockOrderService.createOrder0(sid);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return success ? "秒杀成功" : "秒杀失败";
    }


    /**
     * 采用令牌桶算法限流执行
     *
     * @param sid
     * @return
     */
    @GetMapping("/seckill-1/{sid}")
    public String seckill1(@PathVariable int sid, HttpServletResponse response) {
        boolean success = false;
        try {
            success = stockOrderService.createOrder1(sid);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        if (success){
            return "正在排队秒杀中";
        }else {
            response.setStatus(505);
            return "当前秒杀人员过多，请稍后再试";
        }
    }


}
