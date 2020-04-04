package tsystems.rehab.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

	private Long id;
	private PatientDto patient;
	private TreatmentDto treatment;
	private String pattern;
	private String receiptTimes;
	private String dosage;
	private String status;
	private Date created_at;
	private Date dueDate;

}
