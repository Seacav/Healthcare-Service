package tsystems.rehab.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="appointment")
@NoArgsConstructor
@Getter @Setter
public class Appointment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name="treatment_id")
	private Treatment treatment;
	
	@Column(name="pattern_days")
	private String pattern;
	
	@Column(name="receipt_times")
	private String receiptTimes;
	
	@Column(name="dosage")
	private String dosage;
	
	@Column(name="status")
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="due_date")
	private Date dueDate;
	
}
