package cn.com.zerobug.demo.rabbitmq.routingkey;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/6/27
 */
public class Producer {

    private static String[] LOG_LEVEL = {
            "ERROR", "WARN", "INFO"
    };

    public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();
        channel.exchangeDeclare("ex.routing", BuiltinExchangeType.DIRECT, false, false, null);


        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            String level = LOG_LEVEL[random.nextInt(100) % LOG_LEVEL.length];
            channel.basicPublish("ex.routing", level, null, (level + "消息").getBytes(StandardCharsets.UTF_8));
        }

        channel.close();
        connection.close();
    }

}
