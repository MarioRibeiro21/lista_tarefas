import { Routes } from '@angular/router';
import { TarefasListaComponent } from './tarefas/tarefas-lista/tarefas-lista.component';
import { TarefaDialogComponent } from './tarefas/tarefa-dialog/tarefa-dialog.component';
import { ConfirmDialogComponent } from './shared/confirm-dialog/confirm-dialog.component';

export const routes: Routes = [
    { path: 'home', component: TarefasListaComponent },
    { path: 'home', component: TarefaDialogComponent },
    { path: 'home', component: ConfirmDialogComponent },

];
