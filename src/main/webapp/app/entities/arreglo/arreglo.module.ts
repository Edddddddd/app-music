import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ArregloComponent } from './list/arreglo.component';
import { ArregloDetailComponent } from './detail/arreglo-detail.component';
import { ArregloUpdateComponent } from './update/arreglo-update.component';
import { ArregloDeleteDialogComponent } from './delete/arreglo-delete-dialog.component';
import { ArregloRoutingModule } from './route/arreglo-routing.module';

@NgModule({
  imports: [SharedModule, ArregloRoutingModule],
  declarations: [ArregloComponent, ArregloDetailComponent, ArregloUpdateComponent, ArregloDeleteDialogComponent],
})
export class ArregloModule {}
