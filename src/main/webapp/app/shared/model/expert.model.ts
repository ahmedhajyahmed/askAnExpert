import { Moment } from 'moment';
import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';
import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';
import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';
import { IRendezVous } from 'app/shared/model/rendez-vous.model';

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
    skill1?: string;
    skill2?: string;
    skill3?: string;
    prix?: number;
    note?: number;
    photoContentType?: string;
    photo?: any;
    numRib?: number;
    disponibilites?: INonDisponibilite[];
    historiqueAppels?: IHistoriqueAppel[];
    historiqueChats?: IHistoriqueChat[];
    rendezVous?: IRendezVous[];
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
        public skill1?: string,
        public skill2?: string,
        public skill3?: string,
        public prix?: number,
        public note?: number,
        public photoContentType?: string,
        public photo?: any,
        public numRib?: number,
        public disponibilites?: INonDisponibilite[],
        public historiqueAppels?: IHistoriqueAppel[],
        public historiqueChats?: IHistoriqueChat[],
        public rendezVous?: IRendezVous[]
    ) {}
}
