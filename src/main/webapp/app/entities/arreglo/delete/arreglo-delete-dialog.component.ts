import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IArreglo } from '../arreglo.model';
import { ArregloService } from '../service/arreglo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './arreglo-delete-dialog.component.html',
})
export class ArregloDeleteDialogComponent {
  arreglo?: IArreglo;

  constructor(protected arregloService: ArregloService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arregloService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
