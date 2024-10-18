# Weather-Info-for-Pincode
Overview
A Spring Boot API that retrieves and stores weather information based on pincode and date using OpenWeatherMap API, with data caching in a MySQL database.

Features
Fetch weather info by pincode and date.
Cache results in MySQL to avoid redundant API calls.
Retrieve weather info from OpenWeatherMap if not cached.
Uses Lombok to reduce boilerplate code.

API URL : http://localhost:8081/api/weather/info

Request JSON Data : 
{
  "pincode": "400001",
  "date": "2024-10-18"
}
