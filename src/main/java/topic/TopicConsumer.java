package topic;

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

public class TopicConsumer {

	private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	private static final String subject = "MyTopic";

	public static void main(String[] args) throws JMSException {
		BasicConfigurator.configure();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createTopic(subject);
		MessageConsumer consumer = session.createConsumer(destination);

		System.out.println("Waiting for a message...");
		Message message = consumer.receive();

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
