package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqBasicsMain {

    public static final String QUEUE = "basics";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);
        channel.basicPublish("", QUEUE, null, "Hello there!".getBytes());

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body, "UTF-8"));
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
        Thread.sleep(50L);

        channel.close();
        connection.close();
    }

}
