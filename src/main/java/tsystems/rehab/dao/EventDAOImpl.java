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

	@Override
	public void saveEvents(List<Event> events) {
		Session session = sessionFactory.getCurrentSession();
		events.forEach(event -> session.save(event));
	}
	
	@Override
	public void saveEvent(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(event);
	}
	
	@Override
	public Event getById(long id) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from event as e where e.id=:id")
				.addEntity(Event.class)
				.setParameter("id", id);
		return sqlQuery.getSingleResult();
	}

	@Override
	public Date getNearestDate(long appointmentId) {
		NativeQuery sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select date from event as e "
						+ "where e.appointment_id=:id and e.status='SCHEDULED' and current_timestamp()<e.date "
						+ "order by e.date limit 1");
		sqlQuery.setParameter("id", appointmentId);
		return (Date) sqlQuery.getSingleResult();
	}

	@Override
	public void deleteNearestEvents(long appointmentId) {
		NativeQuery sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("delete from event as e where e.appointment_id=:id and e.status='SCHEDULED' and current_timestamp()<e.date");
		sqlQuery.setParameter("id", appointmentId);
		sqlQuery.executeUpdate();
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
	public List<Event> getAll(int pageSize, int pageNumber, String filterName) {
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from event limit :offset, :limit")
				.addEntity(Event.class)
				.setParameter("limit", pageSize);
		if (pageNumber==1) {
			sqlQuery.setParameter("offset", 0);
		} else {
			sqlQuery.setParameter("offset", pageSize*(pageNumber-1));
		}
		return sqlQuery.getResultList();
	}

	@Override
	public List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName) {
		String query = "select event.* from event ";
		@SuppressWarnings("unchecked")
		NativeQuery<Event> sqlQuery = constructQuery(pageSize, pageNumber, filterName, patientName, query, false);
		return sqlQuery.getResultList();
	}
	
	@Override
	public BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName) {
		String query = "select count(event.id) from event ";
		@SuppressWarnings("unchecked")
		NativeQuery<BigInteger> sqlQuery = constructQuery(pageSize, pageNumber, filterName, patientName, query, true);
		return sqlQuery.getSingleResult();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public NativeQuery constructQuery(int pageSize, int pageNumber, String filterName, 
			String patientName, String startOfQuery, boolean count) {
		String limit = "";
		if (!count)
			limit = "limit :offset, :limit";
		NativeQuery sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery(startOfQuery + 
						"inner join appointment on event.appointment_id=appointment.id " + 
						"inner join patient on appointment.patient_id=patient.id " + 
						"where appointment.status='VALID' and patient.last_name like :name and event.date < :endTime " + 
						"and event.date > :startTime order by event.date asc " + limit)
				.setParameter("name", "%"+patientName+"%");				
		
		if (!count) {
			sqlQuery.addEntity(Event.class);
			int offset = (pageNumber==1) ? 0 : pageSize*(pageNumber-1);
			sqlQuery.setParameter("offset", offset);
			sqlQuery.setParameter("limit", pageSize);
		}
		
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDateTime currentTime = LocalDateTime.now();
		
		if (filterName.equals("hour")) {
			sqlQuery.setParameter("endTime", Timestamp.valueOf(currentTime.withMinute(0).withSecond(0).withNano(0).plusHours(1)));
			sqlQuery.setParameter("startTime", Timestamp.valueOf(currentTime.withMinute(0).withSecond(0).withNano(0)));
		} else if (filterName.equals("today")) {
			sqlQuery.setParameter("endTime", Timestamp.valueOf(LocalDate.now().atStartOfDay().plusHours(24)));
			sqlQuery.setParameter("startTime", Timestamp.valueOf(LocalDate.now().atStartOfDay()));
		} else {
			sqlQuery.setParameter("endTime", Timestamp.valueOf(LocalDate.now().atStartOfDay().plusWeeks(4)));
			sqlQuery.setParameter("startTime", Timestamp.valueOf(LocalDate.now().with(field, 1).atStartOfDay()));
		}
		return sqlQuery;
	}

}
