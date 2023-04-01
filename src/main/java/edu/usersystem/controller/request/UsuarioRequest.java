package edu.usersystem.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UsuarioRequest {

    String nome;

    String username;

    String email;

    String senha;

    Long cep;

    int numero;

    String complemento;

}
