package edu.usersystem.service;

import edu.usersystem.controller.request.UsuarioRequest;
import edu.usersystem.domain.Endereco;
import edu.usersystem.domain.Usuario;
import edu.usersystem.integration.viacep.ViaCepService;
import edu.usersystem.integration.viacep.response.CepResponse;
import edu.usersystem.repository.UserRepository;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import edu.usersystem.service.Exception.EmailInvalidoException;
import edu.usersystem.service.Exception.SenhaInvalidaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private UsuarioService usuarioService;

    /***
     * Cenario de teste:
     * Ao informar dados completos e corretos de cadastro de usuario, o mesmo deve ser criado com sucesso.
     *
     */
    @Test
    public void cria_usuario_com_sucesso() throws Exception {

        when(viaCepService.buscaEnderco(any())).thenReturn(mockCepResponse());
        when(repository.save(any())).thenReturn(mockUsuario());

        Usuario usuario = usuarioService.criaUsuario(mockUsuarioRequest());

        assertNotNull(usuario);
        assertEquals(LocalDate.now(), usuario.getDataCriacao().toLocalDate());
        assertNotNull(usuario.getEndereco());
        assertEquals("AP 101", usuario.getEndereco().getComplemento());
    }


    /***
     * Cenario de teste:
     * Ao informar dados de um usuario que ja existe na base como email, nome ou username, o sistema deve lançar uma
     * exception de usuario ja existente!
     *
     */
    @Test
    public void cria_usuario_com_sucesso_usuario_ja_existente() throws Exception {

        when(viaCepService.buscaEnderco(any())).thenReturn(mockCepResponse());
        doThrow(DataIntegrityViolationException.class).when(repository).save(any());

        assertThrows(DadosDeUsuarioJaExistenteException.class, () -> usuarioService.criaUsuario(mockUsuarioRequest()));
    }

    /***
     * Cenario de teste:
     * Ao informar dados de um usuario com um email invalido, o sistema deve lançar uma exception de email invalido!
     *
     * Para considerar um email valido, o mesmo deve ter o "@", dominio e o "." e alguma outra extensao como "com"
     */
    @Test
    public void cria_usuario_com_erro_validacao_email() {

        UsuarioRequest request = mockUsuarioRequest();
        request.setEmail("emailinvalido");

        assertThrows(EmailInvalidoException.class, () -> usuarioService.criaUsuario(request));
    }

    /***
     * Cenario de teste:
     * Ao informar dados de um usuario com um senha invalido, o sistema deve lançar uma exception de senha invalido!
     *
     * Para a senha ser considerada valida, deve ter 1 letra maiuscula e minuscula, 1 numero, 1 caracter maiusculo e
     * no minumo 6 caracteres no total.
     */
    @Test
    public void cria_usuario_com_erro_validacao_senha() {

        UsuarioRequest request = mockUsuarioRequest();
        request.setSenha("123@");

        assertThrows(SenhaInvalidaException.class, () -> usuarioService.criaUsuario(request));
    }


    private CepResponse mockCepResponse() {
        return CepResponse.builder()
                .cep("38414505")
                .complemento("AP 101.")
                .bairro("Mansour")
                .cidade("Uberlandia")
                .logradouro("Avenida Américo Attiê")
                .build();
    }

    private UsuarioRequest mockUsuarioRequest() {
        return UsuarioRequest.builder()
                .cep(38414505L)
                .email("joaopedrocar@hotmail.com")
                .username("joaopedroc")
                .senha("1234@aA")
                .complemento("AP 101")
                .numero(61)
                .build();
    }

    private Usuario mockUsuario() {
        return Usuario.builder()
                .nome("Joao Pedro Cardoso")
                .username("joaopedroc")
                .email("joaopedro@hotmail.com")
                .senha("1234")
                .endereco(mockEndereco())
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    private Endereco mockEndereco() {
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
