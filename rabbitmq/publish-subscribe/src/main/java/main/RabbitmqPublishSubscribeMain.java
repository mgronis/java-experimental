package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqPublishSubscribeMain {

    public static final String EXCHANGE = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, "fanout");

        String queue1 = channel.queueDeclare().getQueue();
        String queue2 = channel.queueDeclare().getQueue();

        channel.queueBind(queue1, EXCHANGE, "");
        channel.queueBind(queue2, EXCHANGE, "");

        Consumer consumer1 = createConsumer(channel, "Elin");
        Consumer consumer2 = createConsumer(channel, "Malin");

        channel.basicConsume(queue1, true, consumer1);
        channel.basicConsume(queue2, true, consumer2);

        channel.basicPublish(EXCHANGE, "", null, "Hello there!".getBytes());

        Thread.sleep(500L);

        channel.close();
        connection.close();
    }

    private static DefaultConsumer createConsumer(final Channel channel, String name) {
        return new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + ": " + new String(body, "UTF-8"));
            }
        };
    }

}
