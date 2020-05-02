package tsystems.rehab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Patient {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="diagnosis")
	private String diagnosis;
	
	@Column(name="ins_number", nullable=false, unique=true)
	private String insNumber;
	
	@Column(name="doctor_name")
	private String doctorName;
	
	@Column(name="status")
	private String status;
	
}
