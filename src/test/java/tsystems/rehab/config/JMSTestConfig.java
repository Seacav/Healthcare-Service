package tsystems.rehab.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tsystems.rehab.messaging.JmsConfig;

@Configuration
@Import({
	JmsConfig.class
})
public class JMSTestConfig {
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
	}
	
}
