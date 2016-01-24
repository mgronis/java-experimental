package workers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static main.RabbitmqWorkQueueMain.QUEUE;

public class Worker implements Runnable {

    private String name;

    private transient boolean readMore = true;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    if(message.equals("Exit")) {
                        System.out.println(name + " revieced: Exit");
                        System.out.println(name + " terminating...");
                        readMore = false;
                    } else {
                        System.out.println(name + " got a message: " + message);
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        System.err.println(name + ": Was unable to sleep");
                    }
                }
            };

            while (readMore) {
                channel.basicConsume(QUEUE, true, consumer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
