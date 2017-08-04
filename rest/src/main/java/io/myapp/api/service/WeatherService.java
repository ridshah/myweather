package io.myapp.api.service;

import java.util.List;
import java.util.Map;

import io.myapp.api.entity.Weather;

public interface WeatherService {
	
	public List<String> findAll();
	
	public Weather findOne(String city);
	
	public Weather findWeatherbyCityAndProperty(String city, String property);
	
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city);
	
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city);
	
	public Weather create(Weather weather);


	
}
