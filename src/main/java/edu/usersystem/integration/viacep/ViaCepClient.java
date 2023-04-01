package edu.usersystem.integration.viacep;

import edu.usersystem.integration.viacep.response.CepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCepClient", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping(value = "/{cep}/json", consumes = "aplication/json")
    ResponseEntity<CepResponse> buscaEnderecoPorCep(@PathVariable("cep") String cep);

}
