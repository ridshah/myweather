package io.myapp.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.myapp.api.constants.URI;
import io.myapp.api.entity.Weather;
import io.myapp.api.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value=URI.WEATHER)
@Api(tags="weathers")
@CrossOrigin
public class WeatherController {
	
	@Autowired
	private WeatherService service;
	
	public WeatherController(WeatherService service) {
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find All Cities", notes = "Returns a list of cities in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public List<String> findAll(){
		return service.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value=URI.CITY)
	@ApiOperation(value = "Find Weather by City", notes = "Returns the weather of a city if it exists in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public Weather findOne(@PathVariable("city") String city){
		return service.findOne(city);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value=URI.CITYANDPROPERTY)
	@ApiOperation(value = "Find Weather by City and Property", notes = "Returns the weather property of a city if it exists in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public Weather findWeatherbyCityAndProperty(@PathVariable("city") String city, 
			@PathVariable("property") String property ){
		return service.findWeatherbyCityAndProperty(city,property);
	}
	
	@RequestMapping(method = RequestMethod.GET, value=URI.CITYAVERAGEBYHOUR)
	@ApiOperation(value = "Find Hourly Average Weather by City", notes = "Returns the hourly average weather of a city if it exists in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public Map<String, Weather> findHourlyWeatherAveragebyCity(@PathVariable("city") String city){
		return service.findHourlyWeatherAveragebyCity(city);
	}
	
	@RequestMapping(method = RequestMethod.GET, value=URI.CITYAVERAGEBYDAY)
	@ApiOperation(value = "Find Daily Average Weather by City", notes = "Returns the daily average weather of a city if it exists in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public Map<String, Weather> findDailyWeatherAveragebyCity(@PathVariable("city") String city){
		return service.findDailyWeatherAveragebyCity(city);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create Weather of city", notes = "Adds the weather of a city in the app")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error"), })
	public Weather create(@RequestBody Weather weather){
		return service.create(weather);
	}

}
