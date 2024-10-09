package org.seguridad.api_rest.paises.models;

import java.util.List;

import lombok.Data;

@Data
public class PaisResponse {
    private List<Pais> geonames;
}
