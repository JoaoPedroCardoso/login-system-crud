package edu.usersystem;

import edu.usersystem.controller.request.UsuarioRequest;
import edu.usersystem.domain.Endereco;
import edu.usersystem.domain.Usuario;
import edu.usersystem.integration.viacep.response.CepResponse;

import java.time.LocalDateTime;

public class MockClass4Tests {

    public CepResponse mockCepResponse() {
        return CepResponse.builder()
                .cep("38414505")
                .complemento("AP 101.")
                .bairro("Mansour")
                .cidade("Uberlandia")
                .logradouro("Avenida Américo Attiê")
                .build();
    }

    public UsuarioRequest mockUsuarioRequest() {
        return UsuarioRequest.builder()
                .cep(38414505L)
                .email("joaopedrocar@hotmail.com")
                .username("joaopedroc")
                .senha("1234@aA")
                .complemento("AP 101")
                .numero(61)
                .build();
    }

    public Usuario mockUsuario() {
        return Usuario.builder()
                .nome("Joao Pedro Cardoso")
                .username("joaopedroc")
                .email("joaopedro@hotmail.com")
                .senha("1234")
                .endereco(mockEndereco())
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public Endereco mockEndereco() {
        return Endereco.builder()
                .cep(38414505L)
                .numero(61)
                .estado("MG")
                .logradouro("Avenida Américo Attiê")
                .complemento("AP 101")
                .cidade("Uberlandia")
                .bairro("Mansour")
                .build();
    }
}
