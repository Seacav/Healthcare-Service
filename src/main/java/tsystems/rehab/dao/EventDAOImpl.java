package tsystems.rehab.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.EventDAO;
import tsystems.rehab.entity.Event;

@Repository
public class EventDAOImpl implements EventDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String TIME_START = "startTime";
	private static final String TIME_END = "endTime";
	
	private static Logger logger = LogManager.getLogger(EventDAOImpl.class);

	@Override
	public List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = (NativeQuery<Event>) constructQuery(pageSize, pageNumber, filterName, patientName, false);
		return sqlQuery.getResultList();
	}
	
	@Override
	public BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName) {
		@SuppressWarnings("unchecked")
		NativeQuery<BigInteger> sqlQuery = (NativeQuery<BigInteger>) constructQuery(pageSize, pageNumber, filterName, patientName, true);
		return sqlQuery.getSingleResult();
	}
	
	//Constructs query either for getting filtered events or total number of events
	@Override
	public NativeQuery<?> constructQuery(int pageSize, int pageNumber, String filterName, 
			String patientName, boolean count) {
		NativeQuery<?> sqlQuery;
		if (count) {
			sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select count(event.id) from event " + 
						"inner join appointment on event.appointment_id=appointment.id " + 
						"inner join patient on appointment.patient_id=patient.id " + 
						"where appointment.status='VALID' and patient.last_name like :name and event.date < :endTime " + 
						"and event.date > :startTime")
				.setParameter("name", "%"+patientName+"%");
		} else {
			sqlQuery = sessionFactory.getCurrentSession() 
				.createSQLQuery("select event.* from event " + 
						"inner join appointment on event.appointment_id=appointment.id " + 
						"inner join patient on appointment.patient_id=patient.id " + 
						"where appointment.status='VALID' and patient.last_name like :name and event.date < :endTime " + 
						"and event.date > :startTime order by event.date asc "
						+ "limit :offset, :limit")
				.setParameter("name", "%"+patientName+"%");
			sqlQuery.addEntity(Event.class);
			int offset = (pageNumber==1) ? 0 : pageSize*(pageNumber-1);
			sqlQuery.setParameter("offset", offset);
			sqlQuery.setParameter("limit", pageSize);
		}
		
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDateTime currentTime = LocalDateTime.now();
		
		if (filterName.equals("hour")) {
			sqlQuery.setParameter(TIME_END, Timestamp.valueOf(currentTime.withMinute(0).withSecond(0).withNano(0).plusHours(1)));
			sqlQuery.setParameter(TIME_START, Timestamp.valueOf(currentTime.withMinute(0).withSecond(0).withNano(0)));
		} else if (filterName.equals("today")) {
			sqlQuery.setParameter(TIME_END, Timestamp.valueOf(LocalDate.now().atStartOfDay().plusHours(24)));
			sqlQuery.setParameter(TIME_START, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		} else {
			sqlQuery.setParameter(TIME_END, Timestamp.valueOf(LocalDate.now().atStartOfDay().plusWeeks(4)));
			sqlQuery.setParameter(TIME_START, Timestamp.valueOf(LocalDate.now().with(field, 1).atStartOfDay()));
		}
		return sqlQuery;
	}
	
	@Override
	public List<Event> getNearestEvents(long appointmentId) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from event as e where e.appointment_id=:id and e.status='SCHEDULED'")
				.addEntity(Event.class);
		sqlQuery.setParameter("id", appointmentId);
		return sqlQuery.getResultList();
	}
	
	@Override
	public void deleteNearestEvents(long appointmentId) {
		NativeQuery<?> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("delete from event as e where e.appointment_id=:id and e.status='SCHEDULED'");
		sqlQuery.setParameter("id", appointmentId);
		sqlQuery.executeUpdate();
	}
	
	@Override
	public List<Event> getEventsForToday() {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select event.* from event inner join appointment on event.appointment_id=appointment.id"
						+ " where appointment.status='VALID' and event.date < :endTime and event.date > :startTime")
				.addEntity(Event.class);
		sqlQuery.setParameter(TIME_END, Timestamp.valueOf(LocalDate.now().atStartOfDay().plusHours(24)));
		sqlQuery.setParameter(TIME_START, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		return sqlQuery.getResultList();
	}

	@Override
	public List<Event> getByAppointmentId(long appointmentId) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from event as e where e.appointment_id=:id order by e.date desc")
				.addEntity(Event.class)
				.setParameter("id", appointmentId);
		return sqlQuery.getResultList();
	}
	
	@Override
	public void cancelByAppointmentId(long appointmentId) {
		NativeQuery<?> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("update event as e set e.status='CANCELLED' "
						+ "where e.appointment_id=:id and e.status='SCHEDULED'");
		sqlQuery.setParameter("id", appointmentId);
		sqlQuery.executeUpdate();
	}
	
	
	@Override
	public List<Event> getByPatientId(long patientId) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select e.* from event as e inner join appointment as a " + 
						"on e.appointment_id=a.id where " + 
						"a.patient_id=:id and e.date < :endTime and e.date > :startTime")
				.addEntity(Event.class)
				.setParameter("id", patientId);
		sqlQuery.setParameter(TIME_END, Timestamp.valueOf(LocalDate.now().atStartOfDay().plusHours(24)));
		sqlQuery.setParameter(TIME_START, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		return sqlQuery.getResultList();
	}
	
	@Override
	public void cancelByPatientId(long patientId) {
		NativeQuery<?> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("update event " +
						"set event.status='CANCELLED' "
						+ "where event.appointment_id in ("
						+ "select a.id from appointment as a "
						+ "where a.patient_id=:id)");
		sqlQuery.setParameter("id", patientId);
		sqlQuery.executeUpdate();
	}

	@Override
	public Event getById(long id) {
		logger.info("Getting event data");
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from event as e where e.id=:id")
				.addEntity(Event.class)
				.setParameter("id", id);
		return sqlQuery.getSingleResult();
	}

	@Override
	public Date getNearestDate(long appointmentId) {
		NativeQuery<?> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select date from event as e "
						+ "where e.appointment_id=:id and e.status='SCHEDULED' and current_timestamp()<e.date "
						+ "order by e.date limit 1");
		sqlQuery.setParameter("id", appointmentId);
		return (Date) sqlQuery.getSingleResult();
	}

	@Override
	public BigInteger getNumberOfActiveEvents(long appointmentId) {
		@SuppressWarnings("unchecked")
		NativeQuery<BigInteger> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select count(e.id) from event as e where e.appointment_id=:id and e.status='SCHEDULED'")
				.setParameter("id", appointmentId);
		return sqlQuery.getSingleResult();
	}
	
	@Override
	public void saveEvent(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(event);
	}

	@Override
	public void saveEvents(List<Event> events) {
		Session session = sessionFactory.getCurrentSession();
		events.forEach(event -> {
			session.save(event);
		});
	}

}
