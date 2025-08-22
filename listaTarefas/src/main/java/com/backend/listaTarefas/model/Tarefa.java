package com.backend.listaTarefas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "TAREFAS", uniqueConstraints = { @UniqueConstraint( name = "uk_tarefas_nome", columnNames = "nome" ), @UniqueConstraint( name = "uk_tarefas_ordem", columnNames = "ordem" ) } )
public class Tarefa {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID", nullable = false )
	private Long id;

	@Column( name = "NOME", nullable = false )
	private String nomeTarefa;

	@Column( name = "CUSTO", nullable = false, precision = 15, scale = 2 )
	private BigDecimal custo;

	@Column( name = "DATA_LIMITE", nullable = false )
	private LocalDate dataLimite;

	@Column( name = "ORDEM", nullable = false )
	private Integer ordem;

}
