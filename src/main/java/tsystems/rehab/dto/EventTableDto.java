package tsystems.rehab.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EventTableDto {
	
	private Long id;
	private String appointmentPatientFirstName;
	private String appointmentPatientLastName;
	private String appointmentPatientDiagnosis;
	private String appointmentTreatmentName;
	private String appointmentDosage;
	private String status;
	private Date date;

}
