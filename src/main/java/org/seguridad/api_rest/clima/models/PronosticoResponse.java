package org.seguridad.api_rest.clima.models;

import java.util.List;

import lombok.Data;

@Data
public class PronosticoResponse {
    private String ciudad;
    private List<Pronostico> list;

    @Data
    public static class Pronostico {
        private String dt_txt;
        private Main main;

    }

    @Data
    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;
        private double pressure;
        private double humidity;
    }
}
