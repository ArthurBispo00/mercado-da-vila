package br.com.fiap.mercado_da_vila.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel; // Importante para HATEOAS

@Entity
@Table(name = "TDS_TB_MERCADO_VILA")
@Data // Gera Getters, Setters, toString, equals, hashCode
@NoArgsConstructor // Gera construtor sem argumentos
@AllArgsConstructor // Gera construtor com todos os argumentos
public class Produto extends RepresentationModel<Produto> { // Extende RepresentationModel

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String setor;
    private String tamanho;
    private Double preco;
}
