package com.Test.demo.services;

import com.Test.demo.AppCache.AppCache;
import com.Test.demo.Constants.PlaceHolders;
import com.Test.demo.api.response.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServices {

    @Value("${weather_api_key}")
    private String apikey ;
    @Autowired
    private AppCache appCache;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherEntity getweather(String city){
        WeatherEntity weatherEntity = redisService.get("weather_of_" + city, WeatherEntity.class);

        if(weatherEntity!=null){
            return weatherEntity;
        }
        else{
            String finalapi = appCache.Cache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY, apikey).replace(PlaceHolders.CITY, city);
            ResponseEntity<WeatherEntity> Response = restTemplate.exchange(finalapi, HttpMethod.GET, null, WeatherEntity.class);
            WeatherEntity body = Response.getBody();
            if(body!=null){
                redisService.set("weather_of_" + city,body,300L);
            }
            return body;


        }


    }

}
