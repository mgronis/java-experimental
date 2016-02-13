package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqRoutingMain {

    public static final String EXCHANGE = "direct_exchange";
    public static final String OMEGA = "omega";
    public static final String LAMBDA = "lambda";
    public static final String KAPPA = "kappa";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, "direct");

        String queue1 = channel.queueDeclare().getQueue();
        String queue2 = channel.queueDeclare().getQueue();

        channel.queueBind(queue1, EXCHANGE, OMEGA);
        channel.queueBind(queue2, EXCHANGE, LAMBDA);
        channel.queueBind(queue2, EXCHANGE, KAPPA);

        Consumer consumer1 = createConsumer(channel, OMEGA);
        Consumer consumer2 = createConsumer(channel, LAMBDA + " " + KAPPA);

        channel.basicConsume(queue1, true, consumer1);
        channel.basicConsume(queue2, true, consumer2);

        channel.basicPublish(EXCHANGE, OMEGA, null, ("Hello " + OMEGA).getBytes());
        channel.basicPublish(EXCHANGE, LAMBDA, null, ("Hello " + LAMBDA).getBytes());
        channel.basicPublish(EXCHANGE, KAPPA, null, ("Hello " + KAPPA).getBytes());

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
