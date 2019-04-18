package com.yixin.demo.mq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 *  mq 生产者
 *  {@link http://www.rabbitmq.com/tutorials/tutorial-four-java.html}
 */
public class ProduceTest {

	private static final String EXCHANGE_NAME = "alix.taoche.inform";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.145.174");
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setPort(5672);
		factory.setVirtualHost("test");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		String queueName = "alix.taoche.inform";
		String routingKey  = "alix.taoche.inform";
		//channel.queueBind(queueName, EXCHANGE_NAME, routingKey,null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
									   AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(String.format("[%s:%s] received message: %s",envelope.getExchange(),envelope.getRoutingKey(),message));
				
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

}
