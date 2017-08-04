package io.myapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class JPAConfig extends AbstractMongoConfiguration{
	
	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("db.name");
	}
	
	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(env.getProperty("db.url"));
	}

	@Override
	protected String getMappingBasePackage() {
		return "io.myapp.api";
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
		return mongoTemplate;
	}
}
