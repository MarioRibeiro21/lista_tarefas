import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface TarefaDTO { id: number; nomeTarefa: string; custo: number; dataLimite: string; ordem: number; }

@Injectable({
  providedIn: 'root'
})
export class TarefasService {
  private base = 'http://localhost:8080/lista-tarefas';
  constructor(private http: HttpClient) { }

  listar(): Observable<TarefaDTO[]> {
    return this.http.get<TarefaDTO[]>(`${this.base}/listar-tarefas`);
  }

  incluir(dto: TarefaDTO) {
    return this.http.post<TarefaDTO>(`${this.base}/salvar`, dto);
  }

  atualizar(id: number, dto: TarefaDTO) {
    return this.http.put<TarefaDTO>(`${this.base}/salvar`, dto);
  }

  excluir(id: number) {
    return this.http.delete(`${this.base}/${id}`);
  }

  exists(nome: string) {
    return this.http.get<{ exists: boolean }>(`${this.base}/nome-existe`, { params: { nome } });
  }

  reordenar(novaOrdem: Record<number, number>) {
    return this.http.put<TarefaDTO[]>(`${this.base}/reordenar`, novaOrdem);
  }

  mover(id: number, subir: boolean) {
    return this.http.post<TarefaDTO[]>(`${this.base}/${id}/mover`, null, { params: { subir } });
  }

}
