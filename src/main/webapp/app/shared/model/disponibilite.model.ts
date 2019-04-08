import { Moment } from 'moment';
import { IExpert } from 'app/shared/model/expert.model';

export interface IDisponibilite {
    id?: number;
    date?: Moment;
    expert?: IExpert;
}

export class Disponibilite implements IDisponibilite {
    constructor(public id?: number, public date?: Moment, public expert?: IExpert) {}
}
