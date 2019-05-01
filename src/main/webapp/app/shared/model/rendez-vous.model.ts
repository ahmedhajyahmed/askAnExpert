import { Moment } from 'moment';
import { IExpert } from 'app/shared/model/expert.model';

export interface IRendezVous {
    id?: number;
    start?: Moment;
    end?: Moment;
    title?: string;
    allDay?: boolean;
    url?: string;
    expert?: IExpert;
}

export class RendezVous implements IRendezVous {
    constructor(
        public id?: number,
        public start?: Moment,
        public end?: Moment,
        public title?: string,
        public allDay?: boolean,
        public url?: string,
        public expert?: IExpert
    ) {
        this.allDay = this.allDay || false;
    }
}
