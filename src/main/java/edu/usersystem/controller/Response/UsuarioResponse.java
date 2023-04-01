package edu.usersystem.controller.Response;

import edu.usersystem.domain.Endereco;
import edu.usersystem.domain.Usuario;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UsuarioResponse {

    String nome;

    String username;

    String email;

    Long cep;

    String logradouro;

    String bairro;

    String cidade;

    String estado;

    int numero;

    String complemento;

    public static UsuarioResponse converteUsuario(Usuario usuario) {
        Endereco endereco = usuario.getEndereco();
        return  UsuarioResponse.builder()
                .nome(usuario.getNome())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .build();
    }

}
