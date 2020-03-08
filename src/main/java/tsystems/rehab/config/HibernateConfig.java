package tsystems.rehab.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
			dataSource.
				setJdbcUrl("jdbc:mysql://localhost:3306/healthcare?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8");
			dataSource.setUser("student");
			dataSource.setPassword("student");
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		System.out.println(dataSource.getDataSourceName());
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] {"tsystems.rehab.entity"});
		sessionFactory.setHibernateProperties(hibernateProperties());
		System.out.println(sessionFactory.toString());
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		System.out.println(transactionManager.toString());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.c3p0.min_size", "5");
		return hibernateProperties;
	}
	
}









