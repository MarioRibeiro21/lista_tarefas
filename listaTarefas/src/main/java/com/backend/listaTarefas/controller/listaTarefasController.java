package com.backend.listaTarefas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.listaTarefas.dto.TarefaDTO;
import com.backend.listaTarefas.service.ListaTarefasService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "lista-tarefas" )
public class listaTarefasController {

	private final ListaTarefasService service;

	@GetMapping( "/listar-tarefas" )
	public ResponseEntity<?> listarTarefas() {
		return ResponseEntity.ok( service.listarTarefas() );
	}

	@PostMapping( "/salvar" )
	public ResponseEntity<TarefaDTO> salvar( @RequestBody TarefaDTO dto ) {

		return ResponseEntity.ok( service.save( dto ) );
	}

	@DeleteMapping( "/{id}" )
	public void excluir( @PathVariable Long id ) {
		service.excluir( id );
	}

	@GetMapping( "/nome-existe" )
	public Map<String, Boolean> exists( @RequestParam String nome ) {
		return Map.of( "exists", service.nomeExiste( nome ) );
	}

	@PutMapping( "/reordenar" )
	public List<TarefaDTO> reordenar( @RequestBody Map<Long, Integer> novaOrdem ) {
		return service.reordenar( novaOrdem );
	}

	@PostMapping( "/{id}/mover" )
	public List<TarefaDTO> mover( @PathVariable Long id, @RequestParam boolean subir ) {
		return service.mover( id, subir );
	}

}
