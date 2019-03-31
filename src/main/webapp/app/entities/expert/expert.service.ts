import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExpert } from 'app/shared/model/expert.model';

type EntityResponseType = HttpResponse<IExpert>;
type EntityArrayResponseType = HttpResponse<IExpert[]>;

@Injectable({ providedIn: 'root' })
export class ExpertService {
    public resourceUrl = SERVER_API_URL + 'api/experts';

    constructor(protected http: HttpClient) {}

    create(expert: IExpert): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(expert);
        return this.http
            .post<IExpert>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(expert: IExpert): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(expert);
        return this.http
            .put<IExpert>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExpert>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExpert[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(expert: IExpert): IExpert {
        const copy: IExpert = Object.assign({}, expert, {
            date_naissance:
                expert.date_naissance != null && expert.date_naissance.isValid() ? expert.date_naissance.format(DATE_FORMAT) : null,
            disponibilite: expert.disponibilite != null && expert.disponibilite.isValid() ? expert.disponibilite.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date_naissance = res.body.date_naissance != null ? moment(res.body.date_naissance) : null;
            res.body.disponibilite = res.body.disponibilite != null ? moment(res.body.disponibilite) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((expert: IExpert) => {
                expert.date_naissance = expert.date_naissance != null ? moment(expert.date_naissance) : null;
                expert.disponibilite = expert.disponibilite != null ? moment(expert.disponibilite) : null;
            });
        }
        return res;
    }
}
