package cn.com.zerobug.demo.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/6/27
 */
public class MyProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.68.202");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("123123");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();
        channel.queueDeclare("queue.test", false, false, true, null);
        channel.exchangeDeclare("ex.test", BuiltinExchangeType.DIRECT, false, false, null);
        channel.queueBind("queue.test", "ex.test", "hello.world");

        channel.basicPublish("ex.test", "hello.world", null, "hello world".getBytes(StandardCharsets.UTF_8));

        // 关闭通道
        channel.close();
        // 关闭连接
        connection.close();
    }

}
