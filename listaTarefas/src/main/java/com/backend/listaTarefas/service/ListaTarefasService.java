package com.backend.listaTarefas.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.backend.listaTarefas.dto.TarefaDTO;
import com.backend.listaTarefas.exception.BusinessException;
import com.backend.listaTarefas.exception.ResourceNotFoundException;
import com.backend.listaTarefas.model.Tarefa;
import com.backend.listaTarefas.repository.TarefaRepository;
import com.backend.listaTarefas.utils.Converter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListaTarefasService {

	private final TarefaRepository repositorio;

	public List<TarefaDTO> listarTarefas() {
		return Converter.convertList( repositorio.findAllByOrderByOrdemAsc(), TarefaDTO.class );
	}

	@Transactional
	public TarefaDTO save( TarefaDTO tarefaDTO ) {
		if( tarefaDTO.getId() != null && !ObjectUtils.isEmpty( tarefaDTO.getId() ) ) {
			return updateTarefa( tarefaDTO );
		}

		return insertTarefa( tarefaDTO );
	}

	@Transactional
	public void excluir( Long id ) {
		if( !repositorio.existsById( id ) ) {
			throw new ResourceNotFoundException( "Tarefa não encontrada" );
		}
		repositorio.deleteById( id );
	}

	@Transactional
	public List<TarefaDTO> reordenar( Map<Long, Integer> novaOrdem ) {
		long distintos = new HashSet<>( novaOrdem.values() ).size();
		if( distintos != novaOrdem.size() ) {
			throw new BusinessException( "Valores de ordem não podem se repetir." );
		}
		List<Tarefa> todas = repositorio.findAllByOrderByOrdemAsc();
		for( Tarefa t : todas ) {
			Integer n = novaOrdem.get( t.getId() );
			if( n != null ) {
				t.setOrdem( n );
			}
		}
		repositorio.saveAll( todas );
		return listarTarefas();
	}

	@Transactional
	public List<TarefaDTO> mover( Long id, boolean subir ) {
		List<Tarefa> lista = repositorio.findAllByOrderByOrdemAsc();
		int idx = -1;
		for( int i = 0; i < lista.size(); i++ ) {
			if( Objects.equals( lista.get( i ).getId(), id ) ) {
				idx = i;
				break;
			}
		}
		if( idx < 0 ) {
			throw new ResourceNotFoundException( "Tarefa nãoencontrada" );
		}
		if( subir && idx == 0 ) {
			return listarTarefas();
		}
		if( !subir && idx == lista.size() - 1 ) {
			return listarTarefas();
		}
		int j = subir ? idx - 1 : idx + 1;
		Tarefa a = lista.get( idx ), b = lista.get( j );
		int tmp = a.getOrdem();
		a.setOrdem( b.getOrdem() );
		b.setOrdem( tmp );
		repositorio.save( a );
		repositorio.save( b );
		return listarTarefas();

	}

	public boolean nomeExiste( String nome ) {
		return repositorio.existsByNomeTarefaIgnoreCase( nome );
	}

	private TarefaDTO updateTarefa( TarefaDTO tarefaDTO ) {
		Tarefa tarefaBanco = repositorio.findById( tarefaDTO.getId() ).orElseThrow( () -> new ResourceNotFoundException( "Tarefa não encontrada" ) );

		repositorio.findByNomeTarefaIgnoreCase( tarefaDTO.getNomeTarefa() ).filter( x -> !x.getId().equals( tarefaDTO.getId() ) ).ifPresent( x -> {
			throw new BusinessException( "Já existe tarefa com este nome." );
		} );
		tarefaBanco.setNomeTarefa( tarefaDTO.getNomeTarefa() );
		tarefaBanco.setCusto( tarefaDTO.getCusto() );
		tarefaBanco.setDataLimite( tarefaDTO.getDataLimite() );

		return Converter.convert( repositorio.save( tarefaBanco ), TarefaDTO.class );

	}

	private TarefaDTO insertTarefa( TarefaDTO tarefaDTO ) {
		if( repositorio.existsByNomeTarefaIgnoreCase( tarefaDTO.getNomeTarefa() ) ) {
			throw new BusinessException( "Já existe tarefa com este nome." );
		}
		Integer nextOrder = repositorio.findAll().stream().map( Tarefa::getOrdem ).max( Integer::compareTo ).orElse( 0 ) + 1;
		tarefaDTO.setOrdem( nextOrder );
		var tarefa = Converter.convert( tarefaDTO, Tarefa.class );
		return Converter.convert( repositorio.save( tarefa ), TarefaDTO.class );
	}

}
