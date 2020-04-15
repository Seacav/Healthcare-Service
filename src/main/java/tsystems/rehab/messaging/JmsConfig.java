package tsystems.rehab.messaging;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfig {
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("tcp://localhost:61616");
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		jmsTemplate.setDefaultDestination(new ActiveMQQueue("mail.queue"));
		return jmsTemplate;
	}
	
}
