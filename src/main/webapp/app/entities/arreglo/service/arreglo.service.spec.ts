import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArreglo } from '../arreglo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../arreglo.test-samples';

import { ArregloService, RestArreglo } from './arreglo.service';

const requireRestSample: RestArreglo = {
  ...sampleWithRequiredData,
  fechaRecepcion: sampleWithRequiredData.fechaRecepcion?.toJSON(),
  fechaEntrega: sampleWithRequiredData.fechaEntrega?.toJSON(),
};

describe('Arreglo Service', () => {
  let service: ArregloService;
  let httpMock: HttpTestingController;
  let expectedResult: IArreglo | IArreglo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArregloService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Arreglo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const arreglo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(arreglo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Arreglo', () => {
      const arreglo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(arreglo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Arreglo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Arreglo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Arreglo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArregloToCollectionIfMissing', () => {
      it('should add a Arreglo to an empty array', () => {
        const arreglo: IArreglo = sampleWithRequiredData;
        expectedResult = service.addArregloToCollectionIfMissing([], arreglo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arreglo);
      });

      it('should not add a Arreglo to an array that contains it', () => {
        const arreglo: IArreglo = sampleWithRequiredData;
        const arregloCollection: IArreglo[] = [
          {
            ...arreglo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArregloToCollectionIfMissing(arregloCollection, arreglo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Arreglo to an array that doesn't contain it", () => {
        const arreglo: IArreglo = sampleWithRequiredData;
        const arregloCollection: IArreglo[] = [sampleWithPartialData];
        expectedResult = service.addArregloToCollectionIfMissing(arregloCollection, arreglo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arreglo);
      });

      it('should add only unique Arreglo to an array', () => {
        const arregloArray: IArreglo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const arregloCollection: IArreglo[] = [sampleWithRequiredData];
        expectedResult = service.addArregloToCollectionIfMissing(arregloCollection, ...arregloArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const arreglo: IArreglo = sampleWithRequiredData;
        const arreglo2: IArreglo = sampleWithPartialData;
        expectedResult = service.addArregloToCollectionIfMissing([], arreglo, arreglo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(arreglo);
        expect(expectedResult).toContain(arreglo2);
      });

      it('should accept null and undefined values', () => {
        const arreglo: IArreglo = sampleWithRequiredData;
        expectedResult = service.addArregloToCollectionIfMissing([], null, arreglo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(arreglo);
      });

      it('should return initial array if no Arreglo is added', () => {
        const arregloCollection: IArreglo[] = [sampleWithRequiredData];
        expectedResult = service.addArregloToCollectionIfMissing(arregloCollection, undefined, null);
        expect(expectedResult).toEqual(arregloCollection);
      });
    });

    describe('compareArreglo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArreglo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArreglo(entity1, entity2);
        const compareResult2 = service.compareArreglo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArreglo(entity1, entity2);
        const compareResult2 = service.compareArreglo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArreglo(entity1, entity2);
        const compareResult2 = service.compareArreglo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
