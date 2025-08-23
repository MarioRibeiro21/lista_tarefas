import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt);

@NgModule({
  declarations: [],
  imports: [BrowserModule, BrowserAnimationsModule, HttpClientModule,
    ReactiveFormsModule,
    MatTableModule, MatIconModule, MatButtonModule, MatDialogModule,
    MatFormFieldModule, MatInputModule,
    MatDatepickerModule, MatNativeDateModule, MatSnackBarModule,
    DragDropModule],
  bootstrap: [],
   providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' }
  ],
})
export class AppModule { }
