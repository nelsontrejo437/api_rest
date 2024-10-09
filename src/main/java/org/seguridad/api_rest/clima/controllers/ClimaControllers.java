package org.seguridad.api_rest.clima.controllers;

import java.util.List;

import org.seguridad.api_rest.clima.models.ClimaResponse;
import org.seguridad.api_rest.clima.models.ContaminacionResponse;
import org.seguridad.api_rest.clima.models.PronosticoResponse;
import org.seguridad.api_rest.clima.services.ClimaService;
import org.seguridad.api_rest.paises.models.Ciudad;
import org.seguridad.api_rest.paises.models.Pais;
import org.seguridad.api_rest.paises.services.GeonamesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clima")
public class ClimaControllers {
    private ClimaService service;
    private GeonamesServices geonamesServices;

    @Autowired
    public ClimaControllers(final ClimaService service, final GeonamesServices geonamesServices) {
        this.service = service;
        this.geonamesServices = geonamesServices;
    }

    @GetMapping("/{ciudad}")
    public ClimaResponse getClima(@PathVariable String ciudad) {
        return service.getClimaByCity(ciudad);
    }

    @GetMapping("/pronostico/{ciudad}")
    public PronosticoResponse getClimaForDay(@PathVariable String ciudad) {
        return service.getClimaForDayByCity(ciudad);
    }

    @GetMapping("/contaminacion/{ciudad}")
    public ContaminacionResponse getClimaForAirPullution(@PathVariable String ciudad) {
        return service.getClimaForAirPullByCity(ciudad);
    }

    @GetMapping("/paises")
    public List<Pais> getPaises() {
        return geonamesServices.getPaises();
    }

    @GetMapping("/ciudades/{pais}")
    public List<Ciudad> getCiudadesByPais(@PathVariable String pais) {
        return geonamesServices.getCiudades(pais);
    }
}
