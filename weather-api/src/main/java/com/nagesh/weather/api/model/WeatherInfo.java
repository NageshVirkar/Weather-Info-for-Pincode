package com.nagesh.weather.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class WeatherInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String pincode;
	private String date;

	private Double latitude;
	private Double longitude;

	private Double temperature;
	private Double feelsLike;
	private Double tempMin;
	private Double tempMax;
	private Integer pressure;
	private Integer humidity;
	private String weatherMain;
	private String weatherDescription;
}
