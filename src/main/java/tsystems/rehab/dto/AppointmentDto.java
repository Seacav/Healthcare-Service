package tsystems.rehab.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {

	private Long id;
	private PatientDto patient;
	private TreatmentDto treatment;
	private String pattern;
	private String receiptTimes;
	private String dosage;
	private String status;
	private Date createdAt;
	private Date dueDate;

}
