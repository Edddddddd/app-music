import { IUserExtra, NewUserExtra } from './user-extra.model';

export const sampleWithRequiredData: IUserExtra = {
  id: 70386,
};

export const sampleWithPartialData: IUserExtra = {
  id: 38518,
  telefono: 11622,
};

export const sampleWithFullData: IUserExtra = {
  id: 94380,
  rut: 'invoice copy sticky',
  telefono: 73258,
};

export const sampleWithNewData: NewUserExtra = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
