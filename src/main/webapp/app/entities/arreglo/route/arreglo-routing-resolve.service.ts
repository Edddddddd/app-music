import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArreglo } from '../arreglo.model';
import { ArregloService } from '../service/arreglo.service';

@Injectable({ providedIn: 'root' })
export class ArregloRoutingResolveService implements Resolve<IArreglo | null> {
  constructor(protected service: ArregloService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArreglo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((arreglo: HttpResponse<IArreglo>) => {
          if (arreglo.body) {
            return of(arreglo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
