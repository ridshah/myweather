package io.myapp.api.repo;

import java.util.Map;
import java.util.Optional;

import io.myapp.api.entity.Weather;

public interface WeatherRepoCustom {
	
	public Optional<Weather> findWeatherbyCityAndProperty(String city, String property);
	
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city);
	
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city);
	
	public Weather create(Weather weather);

}
