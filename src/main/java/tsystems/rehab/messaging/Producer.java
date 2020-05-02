package tsystems.rehab.messaging;

import java.net.ConnectException;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(String queueName, String message) {
		jmsTemplate.send(queueName, session -> {
			TextMessage msg = session.createTextMessage(message);
			msg.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
			return msg;
		});
	}
	
}
