
import javax.jms.JMSException;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){
        try {
            new Window("James");
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
