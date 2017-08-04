package io.myapp.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.myapp.api.entity.Weather;
import io.myapp.api.exception.BadRequestException;
import io.myapp.api.exception.NotFoundException;
import io.myapp.api.repo.WeatherRepo;
import io.myapp.api.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService{

	@Autowired
	private WeatherRepo repo;
	
	public WeatherServiceImpl(WeatherRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public List<String> findAll() {
		return repo.findAll();
	}

	@Override
	public Weather findOne(String city) {
		return repo.findOne(city)
				.orElseThrow(() -> new 
						NotFoundException("Weather of city " + city + " not available in the database"));
	}
	
	@Override
	public Weather findWeatherbyCityAndProperty(String city, String property) {
		return repo.findWeatherbyCityAndProperty(city, property)
				.orElseThrow(() -> new 
						NotFoundException("Weather of city " + city + " with property" + property + " not available in the database"));
	}
	
	@Override
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city) {
		Optional<Weather> mayExists = repo.findOne(city);
		if (!mayExists.isPresent()) {
			throw new BadRequestException("Weather of city " + city + " not available in the database");
		}
		return repo.findHourlyWeatherAveragebyCity(city);
				
	}
	
	@Override
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city) {
		Optional<Weather> mayExists = repo.findOne(city);
		if (!mayExists.isPresent()) {
			throw new BadRequestException("Weather of city " + city + " not available in the database");
		}
		return repo.findDailyWeatherAveragebyCity(city);
				
	}

	@Override
	public Weather create(Weather weather) {
		return repo.create(weather);
	}

	
	
	

}
