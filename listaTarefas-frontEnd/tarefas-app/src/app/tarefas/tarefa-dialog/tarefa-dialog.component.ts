import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, AbstractControl, AsyncValidatorFn } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { map, timer, switchMap } from 'rxjs';
import { TarefasService, TarefaDTO } from '../../core/services/tarefas.service';
import { MatMomentDateModule, MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';


registerLocaleData(localePt);

@Component({
  selector: 'app-tarefa-dialog',
  templateUrl: './tarefa-dialog.component.html',
  styleUrls: ['./tarefa-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatButtonModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' },
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: false } }
  ]
})
export class TarefaDialogComponent {
  titulo = '';
  custoExibido = '';
  form = this.fb.group({
    nomeTarefa: ['', [Validators.required, Validators.maxLength(255)], [this.nomeUnicoValidator()]],
    custo: [0, [Validators.required, Validators.min(0)]],
    dataLimite: [new Date(), [Validators.required]]
  });

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { modo: 'incluir' | 'editar', tarefa?: TarefaDTO },
    private fb: FormBuilder,
    private api: TarefasService,
    private ref: MatDialogRef<TarefaDialogComponent>
  ) {
    this.titulo = data.modo === 'incluir' ? 'Incluir Tarefa' : 'Editar Tarefa';
    if (data.modo === 'editar' && data.tarefa) {
      const t = data.tarefa;
      this.form.patchValue({
        nomeTarefa: t.nomeTarefa,
        custo: t.custo,
        dataLimite: new Date(t.dataLimite)
      });
    }
  }

  nomeUnicoValidator(): AsyncValidatorFn {
    return (control: AbstractControl) => timer(300).pipe(
      switchMap(() => this.api.exists(control.value)),
      map(res => res.exists ? { nomeEmUso: true } : null)
    );
  }

  salvar() {
    if (this.form.invalid) return;

    const v = this.form.value as any;
    const payload = {
      id: v.id,
      nomeTarefa: v.nomeTarefa,
      custo: Number(v.custo),
      dataLimite: new Date(v.dataLimite).toISOString().substring(0, 10),
      ordem: v.ordem
    };

    const req = this.data.modo === 'incluir'
      ? this.api.incluir(payload)
      : this.api.atualizar(this.data.tarefa!.id, payload);

    req.subscribe({ next: _ => this.ref.close(true) });
  }

  onCancelar() {
    this.ref.close();
  }

  formatarCustoBlur(event: any) {
    let value = event.target.value;

  if (value) {
    // converte vírgula para ponto
    const numero = parseFloat(value.replace(/\./g, '').replace(',', '.'));

    if (!isNaN(numero)) {
      // atualiza o form control com número puro
      this.form.controls['custo'].setValue(numero);

      // atualiza visual apenas no input
      event.target.value = numero.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }
  }
  }
}
