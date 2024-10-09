package org.seguridad.api_rest.clima.services;

import org.seguridad.api_rest.clima.models.ClimaResponse;
import org.seguridad.api_rest.clima.models.ContaminacionResponse;
import org.seguridad.api_rest.clima.models.PronosticoResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ClimaService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate rest = new RestTemplate();

    @Cacheable(value = "climaByCity", key = "#ciudad")
    public ClimaResponse getClimaByCity(String ciudad) {
        try {
            String url = String
                    .format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", ciudad,
                            apiKey);
            return rest.getForObject(url, ClimaResponse.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener el clima para la ciudad: " + ciudad, ex);
        }
    }

    @Cacheable(value = "pronosticoByCity", key = "#ciudad")
    public PronosticoResponse getClimaForDayByCity(String ciudad) {
        try {
            String url = String
                    .format("https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=metric", ciudad,
                            apiKey);
            return rest.getForObject(url, PronosticoResponse.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener el clima para los siguientes dias para la ciudad: " + ciudad,
                    ex);
        }
    }

    @Cacheable(value = "contaminacionByCity", key = "#ciudad")
    public ContaminacionResponse getClimaForAirPullByCity(String ciudad) {
        try {
            String urlCiudad = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", ciudad,
                    apiKey);
            ClimaResponse clima = rest.getForObject(urlCiudad, ClimaResponse.class);

            double lat = clima.getCoord().getLat();
            double lon = clima.getCoord().getLon();

            String urlPull = String.format(
                    "https://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s",
                    lat, lon, apiKey);

            return rest.getForObject(urlPull, ContaminacionResponse.class);
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener la contaminacion de aire para la ciudad: " + ciudad, ex);
        }
    }

    @Scheduled(fixedRate =  7200000) // 2 horas
    @CacheEvict(value = "climaByCity", allEntries = true)
    public void clearClimaCache(){
        System.out.println("Limpiando cache climaByCity.....");
       return;
    }

    @Scheduled(fixedRate =  7200000)
    @CacheEvict(value = "pronosticoByCity", allEntries = true)
    public void clearPronosticoCache() {
        System.out.println("Limpiando cache pronosticoByCity......");
    }

    @Scheduled(fixedRate =  7200000)
    @CacheEvict(value = "contaminacionByCity", allEntries = true)
    public void clearContaminacionCache() {
        System.out.println("Limpiando cache contaminacionByCity......");
    }
}
