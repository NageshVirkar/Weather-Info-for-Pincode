package com.nagesh.weather.api.service;

import com.nagesh.weather.api.model.WeatherInfo;
import com.nagesh.weather.api.model.WeatherResponse;
import com.nagesh.weather.api.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private WeatherRepository weatherRepository;

	private final String API_KEY = "9b3b6fe912b5bbb83e12524f97161d97";
	private final String GEOCODING_URL = "https://api.openweathermap.org/data/2.5/weather?zip={zip},{country}&appid="
			+ API_KEY;
	private final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid="
			+ API_KEY;

	@Override
	public WeatherInfo getWeatherForPincode(String pincode, String date) {
		WeatherInfo weatherInfo = weatherRepository.findByPincodeAndDate(pincode, date).orElse(null);

		if (weatherInfo != null) {
			return weatherInfo;
		}

		weatherInfo = weatherRepository.findByPincode(pincode).orElse(null);
		if (weatherInfo != null) {
			return fetchAndSaveWeatherInfo(pincode, date, weatherInfo.getLatitude(), weatherInfo.getLongitude());
		}

		return fetchAndSaveWeatherInfo(pincode, date);
	}

	private WeatherInfo fetchAndSaveWeatherInfo(String pincode, String date) {
		try {
			String geocodingUrl = GEOCODING_URL.replace("{zip}", pincode).replace("{country}", "in");
			RestTemplate restTemplate = new RestTemplate();
			WeatherResponse geocodingResponse = restTemplate.getForObject(geocodingUrl, WeatherResponse.class);

			if (geocodingResponse == null || geocodingResponse.getCoord() == null) {
				throw new IllegalArgumentException("Invalid pincode provided.");
			}

			double lat = geocodingResponse.getCoord().getLat();
			double lon = geocodingResponse.getCoord().getLon();

			String weatherUrl = WEATHER_URL.replace("{lat}", String.valueOf(lat)).replace("{lon}", String.valueOf(lon));
			WeatherResponse weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponse.class);

			WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setPincode(pincode);
			weatherInfo.setDate(date);
			weatherInfo.setLatitude(lat);
			weatherInfo.setLongitude(lon);
			weatherInfo.setTemperature(weatherResponse.getMain().getTemp());
			weatherInfo.setFeelsLike(weatherResponse.getMain().getFeels_like());
			weatherInfo.setTempMin(weatherResponse.getMain().getTemp_min());
			weatherInfo.setTempMax(weatherResponse.getMain().getTemp_max());
			weatherInfo.setPressure(weatherResponse.getMain().getPressure());
			weatherInfo.setHumidity(weatherResponse.getMain().getHumidity());
			weatherInfo.setWeatherMain(weatherResponse.getWeather().get(0).getMain());
			weatherInfo.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());

			return weatherRepository.save(weatherInfo);
		} catch (HttpClientErrorException e) {
			throw new IllegalArgumentException("Error fetching data from external API: " + e.getMessage());
		} catch (Exception e) {
			throw new IllegalArgumentException("An error occurred: " + e.getMessage());
		}
	}

	private WeatherInfo fetchAndSaveWeatherInfo(String pincode, String date, double lat, double lon) {
		try {
			String weatherUrl = WEATHER_URL.replace("{lat}", String.valueOf(lat)).replace("{lon}", String.valueOf(lon));
			RestTemplate restTemplate = new RestTemplate();
			WeatherResponse weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponse.class);

			WeatherInfo weatherInfo = new WeatherInfo();
			weatherInfo.setPincode(pincode);
			weatherInfo.setDate(date);
			weatherInfo.setLatitude(lat);
			weatherInfo.setLongitude(lon);
			weatherInfo.setTemperature(weatherResponse.getMain().getTemp());
			weatherInfo.setFeelsLike(weatherResponse.getMain().getFeels_like());
			weatherInfo.setTempMin(weatherResponse.getMain().getTemp_min());
			weatherInfo.setTempMax(weatherResponse.getMain().getTemp_max());
			weatherInfo.setPressure(weatherResponse.getMain().getPressure());
			weatherInfo.setHumidity(weatherResponse.getMain().getHumidity());
			weatherInfo.setWeatherMain(weatherResponse.getWeather().get(0).getMain());
			weatherInfo.setWeatherDescription(weatherResponse.getWeather().get(0).getDescription());

			return weatherRepository.save(weatherInfo);
		} catch (HttpClientErrorException e) {
			throw new IllegalArgumentException("Error fetching data from external API: " + e.getMessage());
		} catch (Exception e) {
			throw new IllegalArgumentException("An error occurred: " + e.getMessage());
		}
	}
}
