package com.Test.demo.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class WeatherEntity{

    private Current current;
    @Getter
    @Setter
    public static class Current{

        @JsonProperty("temp_c")
        private double tempC;
        @JsonProperty("temp_f")
        private double tempF;


        @JsonProperty("feelslike_c")
        public double feelslike;


    }
}






