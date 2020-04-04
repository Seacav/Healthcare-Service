package tsystems.rehab.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EventDto {

	private Long id;
	private AppointmentDto appointment;
	private String status;
	private String commentary;
	private Date date;

}
