package main.threaded;

import com.rabbitmq.client.*;
import workers.Worker;

import java.io.IOException;
import java.util.concurrent.*;

public class RabbitmqWorkQueueThreadMain {

    public static final String QUEUE = "workqueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.execute(new Worker("Steve"));
        executor.execute(new Worker("Jenny"));

        sendMessage("Alpha");
        sendMessage("Beta");
        sendMessage("Gamma");
        sendMessage("Delta");
        sendMessage("Exit");
        sendMessage("Exit");

        executor.shutdown();

        try {
            executor.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("TIME OUT!!! SOME SOME TASKS DID NOT FINNISH EXECUTING!!!");
            e.printStackTrace();
        }

        System.exit(0);
    }

    private static void sendMessage(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);

        channel.basicPublish("", QUEUE, null, message.getBytes());

        channel.close();
        connection.close();
    }

}
