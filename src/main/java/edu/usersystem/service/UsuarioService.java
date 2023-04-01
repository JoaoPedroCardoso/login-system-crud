package edu.usersystem.service;

import edu.usersystem.controller.request.UsuarioRequest;
import edu.usersystem.domain.Endereco;
import edu.usersystem.domain.Usuario;
import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.integration.viacep.ViaCepService;
import edu.usersystem.integration.viacep.response.CepResponse;
import edu.usersystem.repository.UserRepository;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import edu.usersystem.service.Exception.EmailInvalidoException;
import edu.usersystem.service.Exception.SenhaInvalidaException;
import edu.usersystem.service.Exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static edu.usersystem.utils.RegexUtils.REGEX_EMAIL;
import static edu.usersystem.utils.RegexUtils.REGEX_SENHA;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ViaCepService viaCepService;

    public Usuario criaUsuario(UsuarioRequest usuarioRequest) throws ViaCepIntegrationException,
            DadosDeUsuarioJaExistenteException, ValidationException {

        validaUsuario(usuarioRequest);

        CepResponse cepResponse = viaCepService.buscaEnderco(usuarioRequest.getCep().toString());

        Endereco endereco = Endereco.builder()
                .cep(usuarioRequest.getCep())
                .logradouro(cepResponse.getLogradouro())
                .cidade(cepResponse.getCidade())
                .bairro(cepResponse.getBairro())
                .complemento(usuarioRequest.getComplemento() == null || usuarioRequest.getComplemento().isEmpty() ?
                        cepResponse.getComplemento() : usuarioRequest.getComplemento())
                .estado(cepResponse.getEstado())
                .numero(usuarioRequest.getNumero())
                .build();

        Usuario usuario = Usuario.builder()
                .nome(usuarioRequest.getNome())
                .username(usuarioRequest.getUsername())
                .email(usuarioRequest.getEmail())
                .senha(usuarioRequest.getSenha())
                .endereco(endereco)
                .dataCriacao(LocalDateTime.now())
                .build();

        try {
            return repository.save(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new DadosDeUsuarioJaExistenteException();
        }
    }

    private void validaUsuario(UsuarioRequest usuarioRequest) throws ValidationException {
        if(usuarioRequest.getEmail().isEmpty() || !usuarioRequest.getEmail().matches(REGEX_EMAIL)) {
            throw new EmailInvalidoException();
        }

        if(usuarioRequest.getSenha().isEmpty() || !usuarioRequest.getSenha().matches(REGEX_SENHA)) {
            throw new SenhaInvalidaException();
        }
    }
}
