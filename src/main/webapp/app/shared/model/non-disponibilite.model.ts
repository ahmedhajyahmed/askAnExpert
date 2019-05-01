import { Moment } from 'moment';
import { IExpert } from 'app/shared/model/expert.model';

export interface INonDisponibilite {
    id?: number;
    date?: Moment;
    expert?: IExpert;
}

export class NonDisponibilite implements INonDisponibilite {
    constructor(public id?: number, public date?: Moment, public expert?: IExpert) {}
}
