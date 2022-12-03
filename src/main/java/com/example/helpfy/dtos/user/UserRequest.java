package com.example.helpfy.dtos.user;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
public class UserRequest {
    @NotEmpty(message = "Email inválido.")
    @Email(message = "Email inválido.")
    private String email;
    @NotEmpty(message = "A senha não pode ser vazia.")
    private String password;
    @NotEmpty(message = "Primeiro nome não pode ser vazio.")
    private String name;
    @NotEmpty(message = "Sobrenome não pode ser vazio.")
    private String lastName;
    private String avatarLink;
}
