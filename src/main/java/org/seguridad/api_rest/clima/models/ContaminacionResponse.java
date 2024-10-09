package org.seguridad.api_rest.clima.models;

import java.util.List;

import lombok.Data;

@Data
public class ContaminacionResponse {
    private List<listElement> list;

    @Data
    public static class listElement {
        private Main main;

        private Components components;

        @Data
        public static class Main {
            private double aqi;
        }

        @Data
        public static class Components {
            private double co;
            private double no;
            private double no2;
            private double o3;
            private double so2;
            private double pm2_5;
            private double pm10;
            private double nh3;
        }
    }
}
