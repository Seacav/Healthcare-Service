package tsystems.rehab.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Import({
	HibernateConfig.class
})
@ComponentScan({"tsystems.rehab"})
public class DatabaseTestConfig {
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.generateUniqueName(false)
			.setName("testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false")
			.setType(EmbeddedDatabaseType.H2)
			.addScript("schema.sql")
			.addScript("data.sql")
			.setScriptEncoding("UTF-8")
			.ignoreFailedDrops(true)
			.build();
	}

}
