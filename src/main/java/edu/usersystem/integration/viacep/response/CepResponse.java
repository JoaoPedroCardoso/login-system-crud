package edu.usersystem.integration.viacep.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CepResponse {
       String cep;

       String logradouro;

       String complemento;

       String bairro;

       @JsonProperty("localidade")
       String cidade;

       @JsonProperty("uf")
       String estado;

}
