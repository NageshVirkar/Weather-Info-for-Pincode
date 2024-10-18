package com.nagesh.weather.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nagesh.weather.api.model.WeatherInfo;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherInfo, Long> {
	Optional<WeatherInfo> findByPincodeAndDate(String pincode, String date);

	Optional<WeatherInfo> findByPincode(String pincode);
}
