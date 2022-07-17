package cn.com.zerobug.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

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
public class MySimpleGetConsumer {

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://root:123123@192.168.68.202:5672/%2f");

        Connection  connection = connectionFactory.newConnection();
        Channel     channel    = connection.createChannel();
        GetResponse basicGet   = channel.basicGet("queue.test", true);

        byte[] body = basicGet.getBody();

        System.out.println(new String(body));

        channel.close ();
        connectionFactory.clone();

    }
}
