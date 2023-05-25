# JMS chat

![image](https://github.com/Sayres11/JMSchat/assets/44787029/eaaf20b0-5c56-4cb7-94d2-7e8d6bf7eacc)

The provided code is implementing a simple chat application using Java Message Service (JMS) for messaging and communication between users. It consists of the following classes:

### JMS:
This class sets up the JMS connection, creates the necessary components (session, publisher, subscriber), and handles sending and receiving messages. It implements the MessageListener interface to listen for incoming messages.

### Window:
This class represents the graphical user interface (GUI) window for the chat application. It extends JFrame and provides the chat area, sending area, and send button. It also handles user input and displays received messages. The Window class interacts with the JMS class to send and receive messages.

### Main:
This is the entry point of the application. It creates an instance of the Window class, initializing the chat window with a username.

In summary, the code establishes a JMS connection, creates a chat window, allows users to send and receive messages, and displays them in the GUI.


## UPD:
To run code you need to start JMS server.
The server can be found in the lib folder
