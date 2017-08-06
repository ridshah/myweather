
package io.myapp.api.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="weathers")
//@JsonInclude(Include.NON_NULL)
public class Weather {
	
	private String city;
	private String description;
	private Integer humidity;
	private Integer pressure;
	private Integer temperature;
	private Wind wind;
	private Date timestamp;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	public Integer getPressure() {
		return pressure;
	}
	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}
	public Integer getTemperature() {
		return temperature;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {		
		this.timestamp = timestamp;
	}
	
	
	@Override
	public String toString() {
		return "Weather [city=" + city + ", description=" + description + ", humidity=" + humidity + ", pressure="
				+ pressure + ", temperature=" + temperature + ", wind=" + wind + ", timestamp=" + timestamp + "]";
	}
	
	

}
