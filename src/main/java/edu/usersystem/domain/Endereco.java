package edu.usersystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long cep;

    String logradouro;

    String bairro;

    String cidade;

    String estado;

    int numero;

    String complemento;

}
