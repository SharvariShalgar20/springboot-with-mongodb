package com.sharvari.JournalApp.service;

import com.sharvari.JournalApp.api.response.WeatherResponse;
import com.sharvari.JournalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city, String country) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);

        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city).replace("<country>", country).replace("<apiKey>", apiKey);
            ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = exchange.getBody();

            if (body != null) {
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }

    }

}
