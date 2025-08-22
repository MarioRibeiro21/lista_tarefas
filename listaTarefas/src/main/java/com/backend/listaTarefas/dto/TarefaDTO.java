package com.backend.listaTarefas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TarefaDTO {

	private Long id;
	private String nomeTarefa;
	private BigDecimal custo;
	private LocalDate dataLimite;
	private Integer ordem;

}
