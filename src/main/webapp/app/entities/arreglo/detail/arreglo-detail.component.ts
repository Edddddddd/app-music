import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArreglo } from '../arreglo.model';

@Component({
  selector: 'jhi-arreglo-detail',
  templateUrl: './arreglo-detail.component.html',
})
export class ArregloDetailComponent implements OnInit {
  arreglo: IArreglo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arreglo }) => {
      this.arreglo = arreglo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
