import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';

type EntityResponseType = HttpResponse<IHistoriqueAppel>;
type EntityArrayResponseType = HttpResponse<IHistoriqueAppel[]>;

@Injectable({ providedIn: 'root' })
export class HistoriqueAppelService {
    public resourceUrl = SERVER_API_URL + 'api/historique-appels';

    constructor(protected http: HttpClient) {}

    create(historiqueAppel: IHistoriqueAppel): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiqueAppel);
        return this.http
            .post<IHistoriqueAppel>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(historiqueAppel: IHistoriqueAppel): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiqueAppel);
        return this.http
            .put<IHistoriqueAppel>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHistoriqueAppel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHistoriqueAppel[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(historiqueAppel: IHistoriqueAppel): IHistoriqueAppel {
        const copy: IHistoriqueAppel = Object.assign({}, historiqueAppel, {
            dateAppel:
                historiqueAppel.dateAppel != null && historiqueAppel.dateAppel.isValid()
                    ? historiqueAppel.dateAppel.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateAppel = res.body.dateAppel != null ? moment(res.body.dateAppel) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((historiqueAppel: IHistoriqueAppel) => {
                historiqueAppel.dateAppel = historiqueAppel.dateAppel != null ? moment(historiqueAppel.dateAppel) : null;
            });
        }
        return res;
    }
}
