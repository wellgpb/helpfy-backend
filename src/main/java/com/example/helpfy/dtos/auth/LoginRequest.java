package com.example.helpfy.dtos.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @Email(message = "Email inválido.")
    private String email;
    @NotEmpty(message = "Senha não pode ser vazia.")
    private String password;
}
