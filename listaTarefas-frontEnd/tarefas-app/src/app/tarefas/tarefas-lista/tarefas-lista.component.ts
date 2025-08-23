import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CdkDragDrop, DragDropModule, moveItemInArray } from '@angular/cdk/drag-drop';
import { TarefasService, TarefaDTO } from '../../core/services/tarefas.service';
import { TarefaDialogComponent } from '../tarefa-dialog/tarefa-dialog.component';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
@Component({
  selector: 'app-tarefas-lista',
  templateUrl: './tarefas-lista.component.html',
  styleUrls: ['./tarefas-lista.component.scss'],
  imports: [CommonModule,
    MatIconModule,
    MatTableModule,
    MatIconModule,
    DragDropModule
  ]
}
)
export class TarefasListaComponent implements OnInit {
  displayed = ['id', 'nomeTarefa', 'custo', 'dataLimite', 'acoes'];
  tarefas: TarefaDTO[] = [];
  carregando = false;

  constructor(private api: TarefasService,
    private dialog: MatDialog,
    private snack: MatSnackBar) { }

  ngOnInit() {
    this.load();
  }
  load() {
    this.carregando = true;
    this.api.listar().subscribe({
      next: r => {
        this.tarefas = r;
        this.carregando = false;
      }, error: _ => { this.carregando = false; }
    });
  }

  destaque(t: TarefaDTO) {
    return t.custo >= 1000;
  }

  incluir() {
    const ref = this.dialog.open(TarefaDialogComponent, {
      width: '420px', data: { modo: 'incluir' }
    });
    ref.afterClosed().subscribe(ok => { if (ok) this.load(); });
  }

  editar(t: TarefaDTO) {
    const ref = this.dialog.open(TarefaDialogComponent, {
      width: '420px',
      data: {
        modo: 'editar',
        tarefa: t
      }
    }
    );
    ref.afterClosed().subscribe(ok => {
      if (ok) this.load();
    });
  }
  excluir(t: TarefaDTO) {
    const ref = this.dialog.open(ConfirmDialogComponent, {
      data: {
        titulo:
          'Excluir', mensagem: `Excluir "${t.nomeTarefa}"?`
      }
    });
    ref.afterClosed().subscribe(sim => {
      if (sim) this.api.excluir(t.id).subscribe({
        next: _ => {
          this.snack.open('Excluído', 'OK', { duration: 1500 }); this.load();
        }
      });
    });
  }
  subir(t: TarefaDTO) {
    this.api.mover(t.id, true).subscribe(r =>
      this.tarefas = r);
  }
  descer(t: TarefaDTO) {
    this.api.mover(t.id, false).subscribe(r =>
      this.tarefas = r);
  }
  drop(ev: CdkDragDrop<TarefaDTO[]>) {
    const anterior = [...this.tarefas];
    moveItemInArray(this.tarefas, ev.previousIndex, ev.currentIndex);
    // Montar nova ordem sequencial 1..n
    const mapa: Record<number, number> = {};
    this.tarefas.forEach((t, idx) => mapa[t.id] = idx + 1);
    this.api.reordenar(mapa).subscribe({
      next: r => this.tarefas = r,
      error: _ => { this.tarefas = anterior; this.snack.open('Erro ao reordenar', 'OK', { duration: 2000 }); }
    });
  }


}
