package edu.usersystem.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique=true)
    String username;

    @Column(unique=true)
    String nome;

    @Column(unique=true)
    String email;

    String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    Endereco endereco;

    LocalDateTime dataCriacao;

}
