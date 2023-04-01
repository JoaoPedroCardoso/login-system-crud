package edu.usersystem.configuration.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErroResponse {

    String codigo;

    String mensagem;

}
