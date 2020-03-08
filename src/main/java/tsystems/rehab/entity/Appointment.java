package tsystems.rehab.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private long id;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@Column(name="drug_name")
	private String drug_name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date created_at;
	
	
	
}
