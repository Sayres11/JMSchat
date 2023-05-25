import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Window extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JTextArea chatArea;
    private JTextArea sendingArea;
    private JButton sendButton;
    JMS jms;
    final JFrame frame = new JFrame("JMS chat application");
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

    public Window(String username) throws NamingException, JMSException {
        frame.setLayout(new BorderLayout());

        sendButton = new JButton();
        sendButton.setText("send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        frame.getContentPane().setLayout(new BorderLayout());

        chatArea = new JTextArea(20, 20);
        sendingArea = new JTextArea(10, 10);
        chatArea.setEditable(false);
        JScrollPane scrollableTextArea = new JScrollPane(chatArea);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollableTextArea, BorderLayout.CENTER);
        frame.add(sendingArea, BorderLayout.EAST);
        frame.add(sendButton, BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jms = new JMS(username);
        jms.start();
    }

    static void getMessage(String message) {
        chatArea.append("\n" + LocalTime.now().format(format) + " " + message);
    }

    public void send() {
        if (!sendingArea.getText().isEmpty()){
            chatArea.append("\n" + LocalTime.now().format(format) + " Me:  " + sendingArea.getText());
            try {
                jms.send(sendingArea.getText());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
            sendingArea.setText("");
        }
        else {
            JOptionPane.showMessageDialog(frame,
                    "Write something:)",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
