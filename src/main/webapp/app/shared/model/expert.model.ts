import { Moment } from 'moment';

export interface IExpert {
    id?: number;
    nom?: string;
    prenom?: string;
    date_naissance?: Moment;
    adresse?: string;
    description?: any;
    domaine?: string;
    profession?: string;
    prix?: number;
    disponibilite?: Moment;
    note?: number;
    photoContentType?: string;
    photo?: any;
    num_rib?: number;
}

export class Expert implements IExpert {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public date_naissance?: Moment,
        public adresse?: string,
        public description?: any,
        public domaine?: string,
        public profession?: string,
        public prix?: number,
        public disponibilite?: Moment,
        public note?: number,
        public photoContentType?: string,
        public photo?: any,
        public num_rib?: number
    ) {}
}
