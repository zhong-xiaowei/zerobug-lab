package cn.com.zerobug.demo.rabbitmq.publishsubscribe;

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
public class Consumer2 {

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();
        channel.queueDeclare("queue.ps2", true, false, true, null);
        channel.exchangeDeclare("ex.ps", BuiltinExchangeType.FANOUT,true,false,null);
        channel.queueBind("queue.ps2", "ex.ps", "");
        channel.basicConsume("queue.ps2", (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println(new String(body));
        }, consumerTag -> {

        });

//        channel.close();
//        connectionFactory.clone();

    }

}
