package io.myapp.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.myapp.api.entity.Weather;
import io.myapp.api.exception.BadRequestException;
import io.myapp.api.exception.NotFoundException;
import io.myapp.api.repo.WeatherRepo;
import io.myapp.api.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService{

	private WeatherRepo repo;
	
	public WeatherServiceImpl(WeatherRepo repo) {
		this.repo = repo;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Weather> findAll() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Weather findByCity(String city) {
		return repo.findByCity(city)
				.orElseThrow(() -> new 
						NotFoundException("Weather of city " + city + " not available in the database"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Weather findWeatherbyCityAndProperty(String city, String property) {
		return repo.findWeatherbyCityAndProperty(city, property)
				.orElseThrow(() -> new 
						NotFoundException("Weather of city " + city + " with property" + property + " not available in the database"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city) {
		Optional<Weather> mayExists = repo.findByCity(city);
		if (!mayExists.isPresent()) {
			throw new BadRequestException("Weather of city " + city + " not available in the database");
		}
		return repo.findHourlyWeatherAveragebyCity(city);
				
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city) {
		Optional<Weather> mayExists = repo.findByCity(city);
		if (!mayExists.isPresent()) {
			throw new BadRequestException("Weather of city " + city + " not available in the database");
		}
		return repo.findDailyWeatherAveragebyCity(city);
				
	}

	@Override
	@Transactional
	public Weather create(Weather weather) {
		return repo.create(weather);
	}

	
	
	

}
