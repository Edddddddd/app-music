import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IArreglo, NewArreglo } from '../arreglo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArreglo for edit and NewArregloFormGroupInput for create.
 */
type ArregloFormGroupInput = IArreglo | PartialWithRequiredKeyOf<NewArreglo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IArreglo | NewArreglo> = Omit<T, 'fechaRecepcion' | 'fechaEntrega'> & {
  fechaRecepcion?: string | null;
  fechaEntrega?: string | null;
};

type ArregloFormRawValue = FormValueOf<IArreglo>;

type NewArregloFormRawValue = FormValueOf<NewArreglo>;

type ArregloFormDefaults = Pick<NewArreglo, 'id' | 'fechaRecepcion' | 'fechaEntrega'>;

type ArregloFormGroupContent = {
  id: FormControl<ArregloFormRawValue['id'] | NewArreglo['id']>;
  rutCliente: FormControl<ArregloFormRawValue['rutCliente']>;
  nombre: FormControl<ArregloFormRawValue['nombre']>;
  telefono: FormControl<ArregloFormRawValue['telefono']>;
  fechaRecepcion: FormControl<ArregloFormRawValue['fechaRecepcion']>;
  fechaEntrega: FormControl<ArregloFormRawValue['fechaEntrega']>;
  nombreInstrumento: FormControl<ArregloFormRawValue['nombreInstrumento']>;
  marca: FormControl<ArregloFormRawValue['marca']>;
  modelo: FormControl<ArregloFormRawValue['modelo']>;
  numeroSerie: FormControl<ArregloFormRawValue['numeroSerie']>;
  diagnostico: FormControl<ArregloFormRawValue['diagnostico']>;
  procedimiento: FormControl<ArregloFormRawValue['procedimiento']>;
  observaciones: FormControl<ArregloFormRawValue['observaciones']>;
  valor: FormControl<ArregloFormRawValue['valor']>;
};

export type ArregloFormGroup = FormGroup<ArregloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArregloFormService {
  createArregloFormGroup(arreglo: ArregloFormGroupInput = { id: null }): ArregloFormGroup {
    const arregloRawValue = this.convertArregloToArregloRawValue({
      ...this.getFormDefaults(),
      ...arreglo,
    });
    return new FormGroup<ArregloFormGroupContent>({
      id: new FormControl(
        { value: arregloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      rutCliente: new FormControl(arregloRawValue.rutCliente),
      nombre: new FormControl(arregloRawValue.nombre),
      telefono: new FormControl(arregloRawValue.telefono),
      fechaRecepcion: new FormControl(arregloRawValue.fechaRecepcion),
      fechaEntrega: new FormControl(arregloRawValue.fechaEntrega),
      nombreInstrumento: new FormControl(arregloRawValue.nombreInstrumento),
      marca: new FormControl(arregloRawValue.marca),
      modelo: new FormControl(arregloRawValue.modelo),
      numeroSerie: new FormControl(arregloRawValue.numeroSerie),
      diagnostico: new FormControl(arregloRawValue.diagnostico),
      procedimiento: new FormControl(arregloRawValue.procedimiento),
      observaciones: new FormControl(arregloRawValue.observaciones),
      valor: new FormControl(arregloRawValue.valor),
    });
  }

  getArreglo(form: ArregloFormGroup): IArreglo | NewArreglo {
    return this.convertArregloRawValueToArreglo(form.getRawValue() as ArregloFormRawValue | NewArregloFormRawValue);
  }

  resetForm(form: ArregloFormGroup, arreglo: ArregloFormGroupInput): void {
    const arregloRawValue = this.convertArregloToArregloRawValue({ ...this.getFormDefaults(), ...arreglo });
    form.reset(
      {
        ...arregloRawValue,
        id: { value: arregloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArregloFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaRecepcion: currentTime,
      fechaEntrega: currentTime,
    };
  }

  private convertArregloRawValueToArreglo(rawArreglo: ArregloFormRawValue | NewArregloFormRawValue): IArreglo | NewArreglo {
    return {
      ...rawArreglo,
      fechaRecepcion: dayjs(rawArreglo.fechaRecepcion, DATE_TIME_FORMAT),
      fechaEntrega: dayjs(rawArreglo.fechaEntrega, DATE_TIME_FORMAT),
    };
  }

  private convertArregloToArregloRawValue(
    arreglo: IArreglo | (Partial<NewArreglo> & ArregloFormDefaults)
  ): ArregloFormRawValue | PartialWithRequiredKeyOf<NewArregloFormRawValue> {
    return {
      ...arreglo,
      fechaRecepcion: arreglo.fechaRecepcion ? arreglo.fechaRecepcion.format(DATE_TIME_FORMAT) : undefined,
      fechaEntrega: arreglo.fechaEntrega ? arreglo.fechaEntrega.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
