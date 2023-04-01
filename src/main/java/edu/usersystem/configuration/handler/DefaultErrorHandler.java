package edu.usersystem.configuration.handler;

import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.service.Exception.DadosDeUsuarioJaExistenteException;
import edu.usersystem.service.Exception.EmailInvalidoException;
import edu.usersystem.service.Exception.SenhaInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultErrorHandler {

    @ExceptionHandler(ViaCepIntegrationException.class)
    public ResponseEntity<ErroResponse>  handleViaCepIntegrationException(ViaCepIntegrationException ex) {
    return ResponseEntity.status(424).body(new ErroResponse("VIA_CEP_INTEGRATION_ERROR",
            "Ocorreu um erro ao tentar realizar a integração com ViaCep"));
    }

    @ExceptionHandler(DadosDeUsuarioJaExistenteException.class)
    public ResponseEntity<ErroResponse>  handleDadosDeUsuarioJaExistenteException(DadosDeUsuarioJaExistenteException ex) {
        return ResponseEntity.status(400).body(new ErroResponse("DADOS_USUARIO_INVALIDO",
                "Os dados informados do usuario são invalidos!"));
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<ErroResponse>  handleEmailInvalidoException(EmailInvalidoException ex) {
        return ResponseEntity.status(400).body(new ErroResponse("EMAIL_INVALIDO",
                "O email informado é invalido!"));
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<ErroResponse>  handleSenhaInvalidaException(SenhaInvalidaException ex) {
        return ResponseEntity.status(400).body(new ErroResponse("SENHA_INVALIDA",
                "A senha não é valida. Lembre de informar 1 letra maiuscula e minuscula, 1 caracter especial e 1 numero!"));
    }

}
