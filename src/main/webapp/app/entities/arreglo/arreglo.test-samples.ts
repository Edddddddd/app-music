import dayjs from 'dayjs/esm';

import { IArreglo, NewArreglo } from './arreglo.model';

export const sampleWithRequiredData: IArreglo = {
  id: 44867,
};

export const sampleWithPartialData: IArreglo = {
  id: 33738,
  rutCliente: 'metrics',
  telefono: 'Funcionalidad bypass sensor',
  fechaRecepcion: dayjs('2023-09-27T06:44'),
  marca: 'Normas Bedfordshire Parafarmacia',
  modelo: 'Urbanización Buckinghamshire Parafarmacia',
  numeroSerie: 'Mejorado Puerta Investment',
  observaciones: 'deposit synthesize',
};

export const sampleWithFullData: IArreglo = {
  id: 95833,
  rutCliente: 'proyección Kina Arquitecto',
  nombre: 'Respuesta deliverables Vasco',
  telefono: 'reboot Acero',
  fechaRecepcion: dayjs('2023-09-27T18:55'),
  fechaEntrega: dayjs('2023-09-27T07:49'),
  nombreInstrumento: 'indexing Analista',
  marca: 'Violeta Plástico Violeta',
  modelo: 'Ariary Verde',
  numeroSerie: 'extranet Consultor',
  diagnostico: 'Ladrillo Artesanal',
  procedimiento: 'withdrawal Hogar',
  observaciones: 'Quinta Algodón',
  valor: 'Seychelles Bebes override',
};

export const sampleWithNewData: NewArreglo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
