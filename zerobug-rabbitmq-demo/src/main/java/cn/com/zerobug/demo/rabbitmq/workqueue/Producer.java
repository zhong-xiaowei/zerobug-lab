package cn.com.zerobug.demo.rabbitmq.workqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/6/27
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();

        channel.queueDeclare("queue.wq", true, false, false, null);
        channel.exchangeDeclare("ex.wq", BuiltinExchangeType.DIRECT, false, false, null);

        channel.queueBind("queue.wq", "ex.wq", "test_wq");

        channel.basicPublish("ex.wq", "test_wq", null, "test_wk".getBytes());

        channel.close();
        connection.close();
    }

}
