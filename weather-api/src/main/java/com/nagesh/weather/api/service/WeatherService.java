package com.nagesh.weather.api.service;

import com.nagesh.weather.api.model.WeatherInfo;

public interface WeatherService {
	WeatherInfo getWeatherForPincode(String pincode, String date);
}
