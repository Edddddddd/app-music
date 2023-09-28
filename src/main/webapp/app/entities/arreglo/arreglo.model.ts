import dayjs from 'dayjs/esm';

export interface IArreglo {
  id: number;
  rutCliente?: string | null;
  nombre?: string | null;
  telefono?: string | null;
  fechaRecepcion?: dayjs.Dayjs | null;
  fechaEntrega?: dayjs.Dayjs | null;
  nombreInstrumento?: string | null;
  marca?: string | null;
  modelo?: string | null;
  numeroSerie?: string | null;
  diagnostico?: string | null;
  procedimiento?: string | null;
  observaciones?: string | null;
  valor?: string | null;
}

export type NewArreglo = Omit<IArreglo, 'id'> & { id: null };
