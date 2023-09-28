import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArreglo, NewArreglo } from '../arreglo.model';

export type PartialUpdateArreglo = Partial<IArreglo> & Pick<IArreglo, 'id'>;

type RestOf<T extends IArreglo | NewArreglo> = Omit<T, 'fechaRecepcion' | 'fechaEntrega'> & {
  fechaRecepcion?: string | null;
  fechaEntrega?: string | null;
};

export type RestArreglo = RestOf<IArreglo>;

export type NewRestArreglo = RestOf<NewArreglo>;

export type PartialUpdateRestArreglo = RestOf<PartialUpdateArreglo>;

export type EntityResponseType = HttpResponse<IArreglo>;
export type EntityArrayResponseType = HttpResponse<IArreglo[]>;

@Injectable({ providedIn: 'root' })
export class ArregloService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/arreglos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(arreglo: NewArreglo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arreglo);
    return this.http
      .post<RestArreglo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(arreglo: IArreglo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arreglo);
    return this.http
      .put<RestArreglo>(`${this.resourceUrl}/${this.getArregloIdentifier(arreglo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(arreglo: PartialUpdateArreglo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arreglo);
    return this.http
      .patch<RestArreglo>(`${this.resourceUrl}/${this.getArregloIdentifier(arreglo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestArreglo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestArreglo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArregloIdentifier(arreglo: Pick<IArreglo, 'id'>): number {
    return arreglo.id;
  }

  compareArreglo(o1: Pick<IArreglo, 'id'> | null, o2: Pick<IArreglo, 'id'> | null): boolean {
    return o1 && o2 ? this.getArregloIdentifier(o1) === this.getArregloIdentifier(o2) : o1 === o2;
  }

  addArregloToCollectionIfMissing<Type extends Pick<IArreglo, 'id'>>(
    arregloCollection: Type[],
    ...arreglosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const arreglos: Type[] = arreglosToCheck.filter(isPresent);
    if (arreglos.length > 0) {
      const arregloCollectionIdentifiers = arregloCollection.map(arregloItem => this.getArregloIdentifier(arregloItem)!);
      const arreglosToAdd = arreglos.filter(arregloItem => {
        const arregloIdentifier = this.getArregloIdentifier(arregloItem);
        if (arregloCollectionIdentifiers.includes(arregloIdentifier)) {
          return false;
        }
        arregloCollectionIdentifiers.push(arregloIdentifier);
        return true;
      });
      return [...arreglosToAdd, ...arregloCollection];
    }
    return arregloCollection;
  }

  protected convertDateFromClient<T extends IArreglo | NewArreglo | PartialUpdateArreglo>(arreglo: T): RestOf<T> {
    return {
      ...arreglo,
      fechaRecepcion: arreglo.fechaRecepcion?.toJSON() ?? null,
      fechaEntrega: arreglo.fechaEntrega?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restArreglo: RestArreglo): IArreglo {
    return {
      ...restArreglo,
      fechaRecepcion: restArreglo.fechaRecepcion ? dayjs(restArreglo.fechaRecepcion) : undefined,
      fechaEntrega: restArreglo.fechaEntrega ? dayjs(restArreglo.fechaEntrega) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestArreglo>): HttpResponse<IArreglo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestArreglo[]>): HttpResponse<IArreglo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
