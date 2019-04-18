package com.yixin.demo.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author yaojie
 * @Description: TODO
 * @date 2018/9/299:28
 */

public class ConsumeTest {

    private static final String EXCHANGE_NAME = "alix.taoche.inform.exchane";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.145.174");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //设置交换机类型direct
        channel.exchangeDeclare("alix.taoche.inform.exchane", "direct",true);
        channel.queueDeclare("alix.taoche.inform", true, false, false, null);

        String message = "message ===EmitLogDirect========from"+ System.currentTimeMillis();

        channel.basicPublish( EXCHANGE_NAME, "alix.taoche.inform",
                MessageProperties.BASIC,
                message.getBytes());
        System.out.println(" [x] Sent '"  + "':'" + message + "'");

        channel.close();
        connection.close();
    }
}
