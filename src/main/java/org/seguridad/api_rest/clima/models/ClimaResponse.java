package org.seguridad.api_rest.clima.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClimaResponse {
@JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Clima[] weather;

    @JsonProperty("coord")
    private Coord coord;

    @Data
    public static class Coord {
        private double lon;
        private double lat;
    }

    @Data
    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;
        private double pressure;
        private double humidity;
    }

    @Data
    public static class Clima {
        private String description;
        private String main;
    }
}
