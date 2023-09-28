import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ArregloFormService, ArregloFormGroup } from './arreglo-form.service';
import { IArreglo } from '../arreglo.model';
import { ArregloService } from '../service/arreglo.service';

@Component({
  selector: 'jhi-arreglo-update',
  templateUrl: './arreglo-update.component.html',
})
export class ArregloUpdateComponent implements OnInit {
  isSaving = false;
  arreglo: IArreglo | null = null;

  editForm: ArregloFormGroup = this.arregloFormService.createArregloFormGroup();

  constructor(
    protected arregloService: ArregloService,
    protected arregloFormService: ArregloFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arreglo }) => {
      this.arreglo = arreglo;
      if (arreglo) {
        this.updateForm(arreglo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arreglo = this.arregloFormService.getArreglo(this.editForm);
    if (arreglo.id !== null) {
      this.subscribeToSaveResponse(this.arregloService.update(arreglo));
    } else {
      this.subscribeToSaveResponse(this.arregloService.create(arreglo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArreglo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(arreglo: IArreglo): void {
    this.arreglo = arreglo;
    this.arregloFormService.resetForm(this.editForm, arreglo);
  }
}
