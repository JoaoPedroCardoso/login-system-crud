package edu.usersystem.integration.handler;

import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultErrorHandler {

    @ExceptionHandler(ViaCepIntegrationException.class)
    public ResponseEntity<ErroResponse>  handleViaCepIntegrationException(ViaCepIntegrationException ex,
                                                                          HttpServletRequest request) {
    return ResponseEntity.status(424).body(new ErroResponse("VIA_CEP_INTEGRATION_ERROR",
            "Ocorreu um erro ao tentar realizar a integração com ViaCep"));
    }

    @ExceptionHandler(DadosDeUsuarioJaExistenteException.class)
    public ResponseEntity<ErroResponse>  handleDadosDeUsuarioJaExistenteException(DadosDeUsuarioJaExistenteException ex,
                                                                          HttpServletRequest request) {
        return ResponseEntity.status(400).body(new ErroResponse("DADOS_USUARIO_INVALIDO",
                "Os dados informados do usuario são invalidos!"));
    }

}
