package tsystems.rehab.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(String message) {
		jmsTemplate.send(session -> session.createTextMessage(message));
	}
	
}
