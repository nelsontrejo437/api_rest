package org.seguridad.api_rest.controllers;

import org.seguridad.api_rest.models.LoginRequest;
import org.seguridad.api_rest.models.Usuarios;
import org.seguridad.api_rest.services.UsuariosServices;
import org.seguridad.api_rest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
private final UsuariosServices services;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsuariosServices services, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.services = services;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuarios> usersCreate(@RequestBody Usuarios users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Usuarios user = services.createUsers(users);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> usersLogin(@RequestBody LoginRequest request){
        Optional<Usuarios> optionalUsers = services.findUsersByUsername(request.getUsername());

        if(optionalUsers.isPresent() && passwordEncoder.matches(request.getPassword(), optionalUsers.get().getPassword())){
            String token = jwtUtils.generateToken(optionalUsers.get().getUsername());
            return ResponseEntity.ok(token);
        }
        else {
            return ResponseEntity.status(401).body("Credenciales invalidas");
        }
    }
}
