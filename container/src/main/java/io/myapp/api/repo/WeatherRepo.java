package io.myapp.api.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.myapp.api.entity.Weather;

public interface WeatherRepo extends MongoRepository<Weather, String>, WeatherRepoCustom{

	public List<Weather> findAll();
	
	public Optional<Weather> findByCity(String city);
	
}
