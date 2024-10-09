package org.seguridad.api_rest.controllers;

import java.util.List;

import org.seguridad.api_rest.models.RegistroConsulta;
import org.seguridad.api_rest.services.UsuariosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas") 
@CrossOrigin(origins = {"*"})
public class ConsultasController {
private final UsuariosServices services;

    @Autowired
    public ConsultasController(final UsuariosServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public List<RegistroConsulta> getConsultasByUsers(){
        String Users = services.getCurrentUsers();
       return services.findConsultasByUsername(Users);
    }
}
