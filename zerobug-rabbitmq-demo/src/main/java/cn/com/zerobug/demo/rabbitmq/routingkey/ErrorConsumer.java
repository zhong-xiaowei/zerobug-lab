package cn.com.zerobug.demo.rabbitmq.routingkey;

import com.rabbitmq.client.*;

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
public class ErrorConsumer {

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection connection = connectionFactory.newConnection();
        Channel    channel    = connection.createChannel();
        channel.queueDeclare("queue.error", false, false, false, null);
        channel.exchangeDeclare("ex.routing", BuiltinExchangeType.DIRECT, false, false, null);
        channel.queueBind("queue.error","ex.routing","ERROR");
        channel.basicConsume("queue.error", new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });

    }

}
