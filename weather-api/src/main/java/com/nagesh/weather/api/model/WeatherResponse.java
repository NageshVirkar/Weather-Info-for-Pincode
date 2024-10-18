package com.nagesh.weather.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

	private Coord coord;
	private List<Weather> weather;
	private Main main;

	@Data
	public static class Coord {
		private double lon;
		private double lat;
	}

	@Data
	public static class Weather {
		private String main;
		private String description;
	}

	@Data
	public static class Main {
		private double temp;
		@JsonProperty("feels_like")
		private double feels_like;
		@JsonProperty("temp_min")
		private double temp_min;
		@JsonProperty("temp_max")
		private double temp_max;
		private int pressure;
		private int humidity;
	}
}
