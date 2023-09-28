import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../arreglo.test-samples';

import { ArregloFormService } from './arreglo-form.service';

describe('Arreglo Form Service', () => {
  let service: ArregloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArregloFormService);
  });

  describe('Service methods', () => {
    describe('createArregloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createArregloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rutCliente: expect.any(Object),
            nombre: expect.any(Object),
            telefono: expect.any(Object),
            fechaRecepcion: expect.any(Object),
            fechaEntrega: expect.any(Object),
            nombreInstrumento: expect.any(Object),
            marca: expect.any(Object),
            modelo: expect.any(Object),
            numeroSerie: expect.any(Object),
            diagnostico: expect.any(Object),
            procedimiento: expect.any(Object),
            observaciones: expect.any(Object),
            valor: expect.any(Object),
          })
        );
      });

      it('passing IArreglo should create a new form with FormGroup', () => {
        const formGroup = service.createArregloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            rutCliente: expect.any(Object),
            nombre: expect.any(Object),
            telefono: expect.any(Object),
            fechaRecepcion: expect.any(Object),
            fechaEntrega: expect.any(Object),
            nombreInstrumento: expect.any(Object),
            marca: expect.any(Object),
            modelo: expect.any(Object),
            numeroSerie: expect.any(Object),
            diagnostico: expect.any(Object),
            procedimiento: expect.any(Object),
            observaciones: expect.any(Object),
            valor: expect.any(Object),
          })
        );
      });
    });

    describe('getArreglo', () => {
      it('should return NewArreglo for default Arreglo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createArregloFormGroup(sampleWithNewData);

        const arreglo = service.getArreglo(formGroup) as any;

        expect(arreglo).toMatchObject(sampleWithNewData);
      });

      it('should return NewArreglo for empty Arreglo initial value', () => {
        const formGroup = service.createArregloFormGroup();

        const arreglo = service.getArreglo(formGroup) as any;

        expect(arreglo).toMatchObject({});
      });

      it('should return IArreglo', () => {
        const formGroup = service.createArregloFormGroup(sampleWithRequiredData);

        const arreglo = service.getArreglo(formGroup) as any;

        expect(arreglo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IArreglo should not enable id FormControl', () => {
        const formGroup = service.createArregloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewArreglo should disable id FormControl', () => {
        const formGroup = service.createArregloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
