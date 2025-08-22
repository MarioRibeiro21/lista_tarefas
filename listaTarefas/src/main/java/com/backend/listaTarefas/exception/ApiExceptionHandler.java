package com.backend.listaTarefas.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler( ResourceNotFoundException.class )
	public ResponseEntity<?> notFound( ResourceNotFoundException ex ) {
		return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( Map.of( "error", ex.getMessage() ) );
	}

	@ExceptionHandler( BusinessException.class )
	public ResponseEntity<?> business( BusinessException ex ) {
		return ResponseEntity.status( HttpStatus.CONFLICT ).body( Map.of( "error", ex.getMessage() ) );
	}

}
