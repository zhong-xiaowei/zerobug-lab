package cn.com.zerobug.demo.rabbitmq.topic;

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
public class ShanghaiConsumer {

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();
        channel.exchangeDeclare("ex.topic", BuiltinExchangeType.TOPIC, false, false, null);
        channel.queueDeclare("queue.topic.shanghai", false, false, false, null);
        channel.queueBind("queue.topic.shanghai","ex.topic","SHANGHAI.#");
        channel.basicConsume("queue.topic.shanghai", (consumerTag, message) -> {
            byte[] body = message.getBody();
            System.out.println(new String(body));
        }, consumerTag -> {

        });
    }
}
