package org.seguridad.api_rest.paises.services;

import java.util.List;

import org.seguridad.api_rest.paises.models.Ciudad;
import org.seguridad.api_rest.paises.models.CiudadResponse;
import org.seguridad.api_rest.paises.models.Pais;
import org.springframework.beans.factory.annotation.Value;
import org.seguridad.api_rest.paises.models.PaisResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeonamesServices {
    @Value("${geonames.username}")
    private String geonameUsername;

    private final RestTemplate rest = new RestTemplate();

    public List<Pais> getPaises() {
        String url = String.format("http://api.geonames.org/countryInfoJSON?username=%s", geonameUsername);
        PaisResponse response = rest.getForObject(url, PaisResponse.class);
        return response.getGeonames();
    }

    public List<Ciudad> getCiudades(String countryCode) {
        String url = String.format("http://api.geonames.org/searchJSON?country=%s&maxRows=10&username=%s", countryCode, geonameUsername);
        CiudadResponse response = rest.getForObject(url, CiudadResponse.class);
        return response.getGeonames();
    }
}
