package com.nagesh.weather.api.controller;

import com.nagesh.weather.api.model.WeatherInfo;
import com.nagesh.weather.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	private static final String INVALID_DATE_FORMAT = "Invalid date format. Expected format: YYYY-MM-DD.";
	private static final String INVALID_PINCODE_FORMAT = "Invalid pincode format. Expected format: 6-digit number.";

	private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	private static final Pattern PINCODE_PATTERN = Pattern.compile("^\\d{6}$");

	@PostMapping("/info")
	public ResponseEntity<?> getWeather(@RequestBody WeatherInfo weatherInfoRequest) {
		String pincode = weatherInfoRequest.getPincode();
		String date = weatherInfoRequest.getDate();

		ResponseEntity<?> validationResponse = validateInputs(pincode, date);
		if (validationResponse != null) {
			return validationResponse;
		}

		WeatherInfo weatherInfoResponse = weatherService.getWeatherForPincode(pincode, date);
		return ResponseEntity.ok(weatherInfoResponse);
	}

	private ResponseEntity<?> validateInputs(String pincode, String date) {
		if (!DATE_PATTERN.matcher(date).matches()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_DATE_FORMAT);
		}

		if (!PINCODE_PATTERN.matcher(pincode).matches()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_PINCODE_FORMAT);
		}

		return null;
	}
}
