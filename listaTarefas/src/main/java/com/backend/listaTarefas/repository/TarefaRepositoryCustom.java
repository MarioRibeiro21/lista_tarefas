package com.backend.listaTarefas.repository;

import java.util.Map;

public interface TarefaRepositoryCustom {

	void reordenar( Map<Long, Integer> novaOrdem );
}
