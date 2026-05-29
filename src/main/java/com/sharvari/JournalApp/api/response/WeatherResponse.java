package com.sharvari.JournalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class WeatherResponse{
    private List<Weather> weather;

    @Getter
    @Setter
    public static class Weather{
        private int id;
        private String main;
        private String description;
        private String icon;
    }

}




