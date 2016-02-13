package main;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqTopicsMain {

    public static final String EXCHANGE = "topic_exchange";
    public static final String GAME_PATTERN = "#.boss.*";
    public static final String VIVALDI_PATTERN = "*.*.vivaldi";
    public static final String CLASSIC_MUSIC_PATTERN = "classic.#";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, "topic");

        String queue1 = channel.queueDeclare().getQueue();
        String queue2 = channel.queueDeclare().getQueue();

        channel.queueBind(queue1, EXCHANGE, GAME_PATTERN);
        channel.queueBind(queue2, EXCHANGE, VIVALDI_PATTERN);
        channel.queueBind(queue2, EXCHANGE, CLASSIC_MUSIC_PATTERN);

        Consumer consumer1 = createConsumer(channel, "Gamer pattern");
        Consumer consumer2 = createConsumer(channel, "Musical pattern");

        channel.basicConsume(queue1, true, consumer1);
        channel.basicConsume(queue2, true, consumer2);

        publishMessage(channel, "megaman.boss.fight");
        publishMessage(channel, "old.composer.vivaldi");
        publishMessage(channel, "classic.rock.van_halen");
        publishMessage(channel, "classic.piano.vivaldi");
        publishMessage(channel, "message.to.no.one");
        publishMessage(channel, "nes.megaman.final.boss.music");

        Thread.sleep(500L);

        channel.close();
        connection.close();
    }

    private static void publishMessage(Channel channel, String messageAndPattern) throws IOException {
        channel.basicPublish(EXCHANGE, messageAndPattern, null, messageAndPattern.getBytes());
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
