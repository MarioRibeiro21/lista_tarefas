package com.backend.listaTarefas.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class TarefaRepositoryCustomImpl implements TarefaRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void reordenar( Map<Long, Integer> novaOrdem ) {
		if( novaOrdem.isEmpty() ) {
			return;
		}

		StringBuilder jpql = new StringBuilder( "UPDATE Tarefa t SET t.ordem = CASE " );
		int i = 0;
		for( Map.Entry<Long, Integer> e : novaOrdem.entrySet() ) {
			jpql.append( "WHEN t.id = :id" ).append( i ).append( " THEN :ordem" ).append( i ).append( " " );
			i++;
		}
		jpql.append( "END WHERE t.id IN (:ids)" );

		Query query = em.createQuery( jpql.toString() );

		i = 0;
		List<Long> ids = new ArrayList<>();
		for( Map.Entry<Long, Integer> e : novaOrdem.entrySet() ) {
			query.setParameter( "id" + i, e.getKey() );
			query.setParameter( "ordem" + i, e.getValue() );
			ids.add( e.getKey() );
			i++;
		}
		query.setParameter( "ids", ids );

		query.executeUpdate();
	}

}
