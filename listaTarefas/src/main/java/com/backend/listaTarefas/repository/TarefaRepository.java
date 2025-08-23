package com.backend.listaTarefas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.listaTarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>, JpaSpecificationExecutor<Tarefa> {

	List<Tarefa> findAllByOrderByOrdemAsc();

	boolean existsByNomeTarefaIgnoreCase( String nomeTarefa );

	Optional<Tarefa> findByNomeTarefaIgnoreCase( String nomeTarefa );
	
	@Modifying
	@Query("UPDATE Tarefa t SET t.ordem = CASE " +
	       "WHEN t.id = :idA THEN :ordemB " +
	       "WHEN t.id = :idB THEN :ordemA END " +
	       "WHERE t.id IN (:idA, :idB)")
	void swapOrdem(@Param("idA") Long idA,
	               @Param("ordemA") int ordemA,
	               @Param("idB") Long idB,
	               @Param("ordemB") int ordemB);
	

}
