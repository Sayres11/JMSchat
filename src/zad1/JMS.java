import javax.jms.*;
import javax.jms.TopicConnection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.soap.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class JMS implements MessageListener {
    private TopicSession pubSession;
    private TopicPublisher publisher;
    private TopicConnection connection;
    private TopicSubscriber subscriber;
    private String username;
    private BufferedReader com;

    public JMS(String username) throws NamingException, JMSException {
        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.exolab.jms.jndi.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:3035/");

        Context context = new InitialContext(properties);
        TopicConnectionFactory conFactory = (TopicConnectionFactory) context.lookup("JmsTopicConnectionFactory");
        TopicConnection connection = conFactory.createTopicConnection();
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic chatTopic = (Topic) context.lookup("topic1");

        TopicPublisher publisher =
                pubSession.createPublisher(chatTopic);
        TopicSubscriber subscriber =
                subSession.createSubscriber(chatTopic, null, true);

        subscriber.setMessageListener(this);

        this.connection = connection;
        this.username = username;
        this.pubSession = pubSession;
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    public void start() {
        try {
            connection.start();
            System.out.println("---Chat started----");
            com =
                    new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String s = com.readLine();
                if (s.equalsIgnoreCase("exit")) {
                    close();
                } else if (!s.isEmpty()) {
                    System.out.println("you send " + s + " ");
                    send(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() throws JMSException {
        connection.close();
        System.exit(0);
    }

    void send(String text) throws JMSException {
        System.out.println("you send");
        TextMessage message = pubSession.createTextMessage();
        message.setText(username + ": " + text);
        publisher.publish(message);
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            Window.getMessage(msg.getText());
            System.out.println(msg.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}