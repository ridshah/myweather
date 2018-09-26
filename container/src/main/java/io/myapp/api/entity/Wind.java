package io.myapp.api.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Wind {
	private Double speed;
	private Double degree;
	
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getDegree() {
		return degree;
	}
	public void setDegree(Double degree) {
		this.degree = degree;
	}
	
	

}
