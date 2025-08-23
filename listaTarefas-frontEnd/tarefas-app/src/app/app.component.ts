import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';
import { TarefasListaComponent } from './tarefas/tarefas-lista/tarefas-lista.component';


@Component({
  selector: 'app-root',
  imports: [
    CommonModule,
    TarefasListaComponent
],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'tarefas-app';
}
