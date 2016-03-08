package topic;

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

public class TopicProducer {

	private static final String subject = "MyTopic";

	public TopicProducer() throws JMSException, NamingException {

		InitialContext jndi = new InitialContext();

		ActiveMQConnectionFactory conFactory = (ActiveMQConnectionFactory) jndi.lookup("ConnectionFactory");
		Connection connection;

		connection = conFactory.createConnection();
		try {
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) jndi.lookup(subject);

			MessageProducer producer = session.createProducer(destination);

			// TextMessage message = session.createTextMessage("Hello World!");
			ObjectMessage message = createMessageFromMyObject(session);
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
			new TopicProducer();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
}
