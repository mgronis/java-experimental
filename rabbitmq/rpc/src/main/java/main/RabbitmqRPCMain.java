package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RabbitmqRPCMain {

    public static final String QUEUE = "RPC_request_queue";
    public static final String SHUTDOWN = "shutdown";

    private static transient boolean hasMore = true;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE, false, consumer);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(createClient("Huey", 2));
        executorService.execute(createClient("Dewey", 6));
        executorService.execute(createClient("Louie", 3));
        executorService.execute(createClient("Webby", 4));
        executorService.execute(createClient("Gyro", 1));
        executorService.execute(createClient("Magica De Spell", 5));
        executorService.execute(terminateCall());

        while(hasMore) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            AMQP.BasicProperties deliveryProperties = delivery.getProperties();
            AMQP.BasicProperties replyProperties = new AMQP.BasicProperties()
                                                            .builder()
                                                            .correlationId(deliveryProperties.getCorrelationId())
                                                            .build();

            final String message = new String(delivery.getBody(), "UTF-8");
            if (isShutdownMessage(message)) {
                hasMore = false;
                System.out.println("Server: Termination has been ordered");
            } else {
                String[] messageSplitted = message.split(":");

                int intToFib = Integer.parseInt(messageSplitted[1]);
                String result = "" + fib(intToFib);

                System.out.println("Server: prepairing answer for " + messageSplitted[0]);

                channel.basicPublish("", deliveryProperties.getReplyTo(), replyProperties, result.getBytes("UTF-8"));
            }

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

        }

        executorService.shutdown();
        boolean termination = executorService.awaitTermination(10, TimeUnit.SECONDS);

        if (!termination){
            executorService.shutdownNow();
        }

        channel.close();
        connection.close();

        System.exit(1);
    }

    private static Runnable createClient(final String name, final int fib) {
        return new Runnable() {

            private transient boolean stillWaitingForMessage = true;

            @Override
            public void run() {
                try {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("localhost");

                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    String replyQueue = channel.queueDeclare().getQueue();
                    QueueingConsumer consumer = new QueueingConsumer(channel);

                    channel.basicConsume(replyQueue, true, consumer);

                    String correlationId = UUID.randomUUID().toString();

                    AMQP.BasicProperties properties = new AMQP.BasicProperties()
                                                                .builder()
                                                                .replyTo(replyQueue)
                                                                .correlationId(correlationId)
                                                                .build();

                    System.out.println(name + ": fib(" + fib + ")?");
                    StringBuilder builder = new StringBuilder().append(name).append(":").append(fib);

                    channel.basicPublish("", QUEUE, properties, builder.toString().getBytes("UTF-8"));

                    while(stillWaitingForMessage) {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        if (delivery.getProperties().getCorrelationId().equals(correlationId)){
                            System.out.println(name + ": fib(" + fib + ") = " + new String(delivery.getBody(), "UTF-8"));
                            stillWaitingForMessage = false;
                        }
                    }

                    channel.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private static Runnable terminateCall() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);

                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("localhost");

                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    String correlationId = UUID.randomUUID().toString();

                    AMQP.BasicProperties properties = new AMQP.BasicProperties()
                            .builder()
                            .correlationId(correlationId)
                            .build();
                    channel.basicPublish("", QUEUE, properties, SHUTDOWN.getBytes("UTF-8"));


                    channel.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static boolean isShutdownMessage(String message) {
        return message.equals(SHUTDOWN);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }

}
