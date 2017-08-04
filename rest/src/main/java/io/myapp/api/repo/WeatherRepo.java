package io.myapp.api.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.myapp.api.entity.Weather;

public interface WeatherRepo{

	public List<String> findAll();
	
	public Optional<Weather> findOne(String city);
	
	public Optional<Weather> findWeatherbyCityAndProperty(String city, String property);
	
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city);
	
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city);
	
	public Weather create(Weather weather);

	
}
