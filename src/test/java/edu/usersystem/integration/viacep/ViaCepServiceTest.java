package edu.usersystem.integration.viacep;

import edu.usersystem.MockClass4Tests;
import edu.usersystem.integration.viacep.Exception.ViaCepIntegrationException;
import edu.usersystem.integration.viacep.response.CepResponse;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ViaCepServiceTest extends MockClass4Tests {

    @Mock
    private ViaCepClient client;

    @InjectMocks
    private ViaCepService viaCepService;

    /***
     * Cenario de teste:
     * Ao informar um cep valido, o sistema deve buscar o cep no via cep e retornar com sucesso.
     */
    @Test
    public void busca_cep_com_sucesso() throws Exception {

        CepResponse cepResponseMock = mockCepResponse();
        when(client.buscaEnderecoPorCep(any())).thenReturn(ResponseEntity.of(Optional.of(cepResponseMock)));

        CepResponse response = viaCepService.buscaEnderco("38404505");

        assertNotNull(response);
    }

    /***
     * Cenario de teste:
     * Ao informar um cep invalido, o sistema deve retornar badRequest e lançar a exception de ViaCepIntegrationException.
     */
    @Test
    public void busca_cep_com_erro_integracao() {

        doThrow(FeignException.class).when(client).buscaEnderecoPorCep(any());

        assertThrows(ViaCepIntegrationException.class, () -> viaCepService.buscaEnderco("38404505000000"));
    }

}
