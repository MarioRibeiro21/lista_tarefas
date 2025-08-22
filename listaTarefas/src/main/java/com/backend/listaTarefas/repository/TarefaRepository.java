package com.backend.listaTarefas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.backend.listaTarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>, JpaSpecificationExecutor<Tarefa> {

	List<Tarefa> findAllByOrderByOrdemAsc();

	boolean existsByNomeTarefaIgnoreCase( String nomeTarefa );

	Optional<Tarefa> findByNomeTarefaIgnoreCase( String nomeTarefa );

}
