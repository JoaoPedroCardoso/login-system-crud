package edu.usersystem.controller;

import edu.usersystem.controller.Response.UsuarioResponse;
import edu.usersystem.controller.request.UsuarioRequest;
import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import edu.usersystem.service.Exception.ValidationException;
import edu.usersystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static edu.usersystem.controller.Response.UsuarioResponse.converteUsuario;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @RequestMapping("/usuario")
    private UsuarioResponse criaUsuario(@RequestBody UsuarioRequest usuarioRequest)
            throws ViaCepIntegrationException, DadosDeUsuarioJaExistenteException, ValidationException {
        return converteUsuario(usuarioService.criaUsuario(usuarioRequest));
    }

}
