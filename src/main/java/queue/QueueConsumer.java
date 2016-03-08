package queue;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;

public class QueueConsumer {
	// URL of the JMS server
	private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will receive messages from
	private static final String subject = "MyQueue";

	public static void main(String[] args) throws JMSException {
		BasicConfigurator.configure();
		// Getting JMS connection from the server
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Getting the queue
		Destination destination = session.createQueue(subject);

		// MessageConsumer is used for receiving (consuming) messages
		MessageConsumer consumer = session.createConsumer(destination);

		// Here we receive the message.
		// By default this call is blocking, which means it will wait
		// for a message to arrive on the queue.
		System.out.println("Waiting for a message...");
		Message message = consumer.receive();

		// There are many types of Message and TextMessage
		// is just one of them. Producer sent us a TextMessage
		// so we must cast to it to get access to its .getText()
		// method.
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("Received message: " + textMessage.getText());
		} else if (message instanceof ObjectMessage) {
			Serializable myMessage = ((ObjectMessage) message).getObject();
			System.out.println("Received message: " + myMessage);
		}
		connection.close();
	}
}
