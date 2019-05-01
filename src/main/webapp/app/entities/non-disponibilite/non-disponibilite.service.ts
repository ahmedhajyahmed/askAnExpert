import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';

type EntityResponseType = HttpResponse<INonDisponibilite>;
type EntityArrayResponseType = HttpResponse<INonDisponibilite[]>;

@Injectable({ providedIn: 'root' })
export class NonDisponibiliteService {
    public resourceUrl = SERVER_API_URL + 'api/non-disponibilites';

    constructor(protected http: HttpClient) {}

    create(nonDisponibilite: INonDisponibilite): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nonDisponibilite);
        return this.http
            .post<INonDisponibilite>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(nonDisponibilite: INonDisponibilite): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(nonDisponibilite);
        return this.http
            .put<INonDisponibilite>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INonDisponibilite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INonDisponibilite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(nonDisponibilite: INonDisponibilite): INonDisponibilite {
        const copy: INonDisponibilite = Object.assign({}, nonDisponibilite, {
            date: nonDisponibilite.date != null && nonDisponibilite.date.isValid() ? nonDisponibilite.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((nonDisponibilite: INonDisponibilite) => {
                nonDisponibilite.date = nonDisponibilite.date != null ? moment(nonDisponibilite.date) : null;
            });
        }
        return res;
    }
}
