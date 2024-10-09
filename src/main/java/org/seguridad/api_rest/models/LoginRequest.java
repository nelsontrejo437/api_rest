package org.seguridad.api_rest.models;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
