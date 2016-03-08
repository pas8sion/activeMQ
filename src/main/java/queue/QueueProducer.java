package queue;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

import activeMQ.MyMessage;

public class QueueProducer {

	private static final String subject = "MyQueue";

	public QueueProducer() throws JMSException, NamingException {

		// Obtain a JNDI connection
		InitialContext jndi = new InitialContext();

		// Look up a JMS connection factory
		ActiveMQConnectionFactory conFactory = (ActiveMQConnectionFactory) jndi.lookup("ConnectionFactory");
		Connection connection;

		// Getting JMS connection from the server and starting it
		connection = conFactory.createConnection();
		try {
			connection.start();

			// JMS messages are sent and received using a Session. We will
			// create here a non-transactional session object. If you want
			// to use transactions you should set the first parameter to "true"
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = (Destination) jndi.lookup(subject);

			// MessageProducer is used for sending messages (as opposed
			// to MessageConsumer which is used for receiving them)
			MessageProducer producer = session.createProducer(destination);

			// We will send a small text message saying "Hello World!"
			// TextMessage message = session.createTextMessage("Hello World!");
			ObjectMessage message = createMessageFromMyObject(session);

			// Here we are sending the message!
			producer.send(message);
			// System.out.println("Sent message: " + message.getText());
			System.out.println("Sent message: " + message.getObject());
		} finally {
			connection.close();
		}
	}

	private ObjectMessage createMessageFromMyObject(Session session) throws JMSException {
		return session.createObjectMessage(new MyMessage(1L, 1, "MyMessage 1", new Date()));
	}

	public static void main(String[] args) throws JMSException {
		try {
			BasicConfigurator.configure();
			new QueueProducer();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}
