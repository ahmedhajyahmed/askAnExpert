import { Moment } from 'moment';
import { IDisponibilite } from 'app/shared/model/disponibilite.model';
import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';
import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';

export const enum Domaine {
    Informatique = 'Informatique',
    Sante = 'Sante',
    Marketing = 'Marketing',
    Communication = 'Communication',
    Juridique = 'Juridique',
    Finance = 'Finance'
}

export interface IExpert {
    id?: number;
    nom?: string;
    prenom?: string;
    dateNaissance?: Moment;
    adresse?: string;
    description?: any;
    domaine?: Domaine;
    profession?: string;
    prix?: number;
    note?: number;
    photoContentType?: string;
    photo?: any;
    numRib?: number;
    disponibilites?: IDisponibilite[];
    historiqueAppels?: IHistoriqueAppel[];
    historiqueChats?: IHistoriqueChat[];
}

export class Expert implements IExpert {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public dateNaissance?: Moment,
        public adresse?: string,
        public description?: any,
        public domaine?: Domaine,
        public profession?: string,
        public prix?: number,
        public note?: number,
        public photoContentType?: string,
        public photo?: any,
        public numRib?: number,
        public disponibilites?: IDisponibilite[],
        public historiqueAppels?: IHistoriqueAppel[],
        public historiqueChats?: IHistoriqueChat[]
    ) {}
}
