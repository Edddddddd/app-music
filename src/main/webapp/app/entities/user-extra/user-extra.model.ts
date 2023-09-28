import { IUser } from 'app/entities/user/user.model';

export interface IUserExtra {
  id: number;
  rut?: string | null;
  telefono?: number | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewUserExtra = Omit<IUserExtra, 'id'> & { id: null };
