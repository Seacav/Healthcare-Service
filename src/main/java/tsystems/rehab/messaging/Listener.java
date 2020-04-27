package tsystems.rehab.messaging;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tsystems.rehab.controller.AppointmentController;
import tsystems.rehab.dto.EventTableDto;
import tsystems.rehab.dto.InfotableDto;
import tsystems.rehab.service.blueprints.EventService;

@Component
public class Listener {
	
	private static Logger logger = LogManager.getLogger(Listener.class.getName());
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private EventService eventService;
	
	@JmsListener(destination="table-to-main.queue")
	public void receiveMessage(Message message) throws JMSException, JsonProcessingException{
		logger.info("MainApp Received message {}", message);
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			logger.info(msg.getText());
			if (msg.getText().equals("DATA")) {
				List<EventTableDto> events = eventService.getEventsForToday();
				InfotableDto infotable = InfotableDto.builder().flag("DATA").events(events).build();
				producer.sendMessage("main-to-table.queue", new ObjectMapper().writeValueAsString(infotable));
			}
		}
	}

}
