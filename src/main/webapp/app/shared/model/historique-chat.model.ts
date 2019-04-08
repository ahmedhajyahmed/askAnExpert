import { Moment } from 'moment';
import { IExpert } from 'app/shared/model/expert.model';

export interface IHistoriqueChat {
    id?: number;
    dateAppel?: Moment;
    description?: any;
    nomClient?: string;
    expert?: IExpert;
}

export class HistoriqueChat implements IHistoriqueChat {
    constructor(
        public id?: number,
        public dateAppel?: Moment,
        public description?: any,
        public nomClient?: string,
        public expert?: IExpert
    ) {}
}
