package main;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import event.AcmeEvent;
import event.AcmeEventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventBusMain {

    private static final Color[] backgrounds = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();

        JPanel receivPanel = new JPanel();
        receivPanel.add(new JLabel("Reciever"));

        JFrame receiver = new JFrame();
        receiver.setSize(new Dimension(300, 300));
        receiver.add(receivPanel);

        eventBus.register(new AcmeEventHandler() {

            private int colorIndex = 0;

            @Subscribe
            @Override
            public void handleEvent(AcmeEvent event) {
                receivPanel.setBackground(getBackground());
            }

            private Color getBackground() {
                return backgrounds[colorIndex++ % backgrounds.length];
            }
        });

        receiver.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        receiver.setVisible(true);

        JPanel sendPanel = new JPanel();
        JButton sendButton = new JButton("Send");
        sendPanel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventBus.post(new AcmeEvent());
            }
        });

        JFrame send = new JFrame();
        send.setSize(new Dimension(100, 100));
        send.add(sendPanel);

        send.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        send.setVisible(true);
    }

}
