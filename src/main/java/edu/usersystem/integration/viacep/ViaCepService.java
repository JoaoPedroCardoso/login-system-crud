package edu.usersystem.integration.viacep;

import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.integration.viacep.response.CepResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ViaCepService {

    @Autowired
    private ViaCepClient client;

    public CepResponse buscaEnderco(String cep) throws ViaCepIntegrationException {
        try{
            return client.buscaEnderecoPorCep(cep).getBody();
        } catch (FeignException ex) {
            throw new ViaCepIntegrationException();
        }
    }

}
