import { Moment } from 'moment';
import { IExpert } from 'app/shared/model/expert.model';

export interface IHistoriqueAppel {
    id?: number;
    dateAppel?: Moment;
    description?: any;
    nomClient?: string;
    expert?: IExpert;
}

export class HistoriqueAppel implements IHistoriqueAppel {
    constructor(
        public id?: number,
        public dateAppel?: Moment,
        public description?: any,
        public nomClient?: string,
        public expert?: IExpert
    ) {}
}
