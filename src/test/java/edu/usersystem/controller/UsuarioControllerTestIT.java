package edu.usersystem.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.usersystem.MockClass4Tests;
import edu.usersystem.TesteUtils;
import edu.usersystem.UserSystemApplication;
import edu.usersystem.configuration.handler.ErroResponse;
import edu.usersystem.controller.Response.UsuarioResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserSystemApplication.class)
public class UsuarioControllerTestIT extends MockClass4Tests {

    @LocalServerPort
    private int port;

    private WireMockServer server;

    @BeforeEach
    public void setupEach() {
        RestAssured.port = port;
        server = new WireMockServer(options().dynamicPort());
        server.start();
    }

    @After
    public void resetMocks() {
        server.resetAll();
        RestAssured.reset();
    }


    /***
     * Cenario de teste:
     * Ao chamar o servico rest /usuario e informar todas as infos corretas, deve ser criado o usuario com sucesso
     * integrando-se no viaCep para busca do endereco.
     *
     * Deve retornar os dados do usuario cadastrado.
     */
    @Test
    public void cria_usuario_com_sucesso() {
        stubParaBuscarCepNoViaCep();

        UsuarioResponse response = given()
                        .contentType(ContentType.JSON)
                        .body(TesteUtils.loadPayload("/requestes/cadastra_usuario_sucesso.json"))
                        .post("/usuario").then().statusCode(201).extract().response().as(UsuarioResponse.class);

        assertNotNull(response);
        assertNotNull(response.getNome());
        assertNotNull(response.getUsername());
        assertNotNull(response.getBairro());
        assertNotNull(response.getEmail());
        assertNotNull(response.getCidade());
        assertNotNull(response.getComplemento());
        assertNotNull(response.getCep());
        assertNotNull(response.getLogradouro());
    }

    /***
     * Cenario de teste:
     * Ao informar dados de um usuario com um email invalido, o sistema deve retornar bad request(400) e uma mensagem
     * dizendo que o email esta invalido.
     *
     * Para considerar um email valido, o mesmo deve ter o "@", dominio e o "." e alguma outra extensao como "com"
     */
    @Test
    public void cria_usuario_com_email_invalido() {
        stubParaBuscarCepNoViaCep();

        ErroResponse response = given()
                        .contentType(ContentType.JSON)
                        .body(TesteUtils.loadPayload("/requestes/cadastra_usuario_email_invalido.json"))
                        .post("/usuario").then().statusCode(400).extract().response().as(ErroResponse.class);

        assertNotNull(response);
        assertEquals("EMAIL_INVALIDO", response.getCodigo());
        assertNotNull("O email informado é invalido!", response.getMensagem());
    }

    /***
     * Cenario de teste:
     * Ao informar dados de um usuario com um senha invalido, o sistema deve retornar bad request(400) e uma mensagem
     * dizendo que a senha é invalida e lembra-lo das regras
     *
     * Para a senha ser considerada valida, deve ter 1 letra maiuscula e minuscula, 1 numero, 1 caracter maiusculo e
     * no minumo 6 caracteres no total.
     */
    @Test
    public void cria_usuario_com_senha_invalido() {
        stubParaBuscarCepNoViaCep();

        ErroResponse response = given()
                .contentType(ContentType.JSON)
                .body(TesteUtils.loadPayload("/requestes/cadastra_usuario_senha_invalido.json"))
                .post("/usuario").then().statusCode(400).extract().response().as(ErroResponse.class);

        assertNotNull(response);
        assertEquals("SENHA_INVALIDA", response.getCodigo());
        assertNotNull("A senha não é valida. Lembre de informar 1 letra maiuscula e minuscula, 1 caracter " +
                "especial e 1 numero!", response.getMensagem());
    }


    /***
     * Cenario de teste:
     * Ao informar dados de um usuario que ja existe na base como email, nome ou username, o sistema deve retornar
     * badRequest(400) e retornar uma mensagem de DADOS_USUARIO_JA_CADASTRADO.
     */
    @Test
    public void cria_usuario_com_duplicidade() {
        stubParaBuscarCepNoViaCep();

        UsuarioResponse primeiroRequest = given()
                .contentType(ContentType.JSON)
                .body(TesteUtils.loadPayload("/requestes/cadastra_usuario_duplicidade.json"))
                .post("/usuario").then().statusCode(201).extract().response().as(UsuarioResponse.class);

        ErroResponse error = given()
                .contentType(ContentType.JSON)
                .body(TesteUtils.loadPayload("/requestes/cadastra_usuario_duplicidade.json"))
                .post("/usuario").then().statusCode(400).extract().response().as(ErroResponse.class);

        assertNotNull(primeiroRequest);
        assertNotNull(error);
        assertEquals("DADOS_USUARIO_JA_CADASTRADO", error.getCodigo());
        assertNotNull("Os dados informados do usuario representam um usuario ja cadastrado!", error.getMensagem());
    }

    private void stubParaBuscarCepNoViaCep() {
        server.stubFor(get(urlEqualTo("/ws/38405326/json"))
                .willReturn(okJson(TesteUtils.loadPayload("/responses/viacep/viacep_sucesso.json"))));
    }

}
