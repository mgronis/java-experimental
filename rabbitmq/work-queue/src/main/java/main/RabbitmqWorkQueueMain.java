package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqWorkQueueMain {

    public static final String QUEUE = "workqueue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);

        Consumer consumer1 = consumerWithName(channel, "kalle");
        Consumer consumer2 = consumerWithName(channel, "lisa");

        channel.basicConsume(QUEUE, true, consumer1);
        channel.basicConsume(QUEUE, true, consumer2);

        channel.basicPublish("", QUEUE, null, "alpha".getBytes());
        channel.basicPublish("", QUEUE, null, "beta".getBytes());
        channel.basicPublish("", QUEUE, null, "gamma".getBytes());
        channel.basicPublish("", QUEUE, null, "delta".getBytes());

        Thread.sleep(5000L);
        // Waiting for messages to be handled

        channel.close();
        connection.close();
    }

    private static DefaultConsumer consumerWithName(final Channel channel, final String name) {
        return new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " got a message: " + message);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    System.err.println(name + ": Was unable to sleep");
                }
            }
        };
    }

}