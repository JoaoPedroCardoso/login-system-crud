package edu.usersystem.controller;

import edu.usersystem.controller.Response.UsuarioResponse;
import edu.usersystem.controller.request.UsuarioRequest;
import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import edu.usersystem.service.Exception.ValidationException;
import edu.usersystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static edu.usersystem.controller.Response.UsuarioResponse.converteUsuario;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @RequestMapping("/usuario")
    @ResponseStatus(code = HttpStatus.CREATED)
    private UsuarioResponse criaUsuario(@RequestBody UsuarioRequest usuarioRequest)
            throws ViaCepIntegrationException, DadosDeUsuarioJaExistenteException, ValidationException {
        return converteUsuario(usuarioService.criaUsuario(usuarioRequest));
    }

}
