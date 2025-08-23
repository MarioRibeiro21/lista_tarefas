import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogActions, MatDialogContent } from '@angular/material/dialog';



@Component({
  selector: 'app-confirm-dialog',
  imports: [MatDialogActions, MatDialogContent],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.scss'
})
export class ConfirmDialogComponent {

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {
      titulo: string;
      mensagem: string
    },
    public ref: MatDialogRef<ConfirmDialogComponent>
  ) { }

}
