import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';

type EntityResponseType = HttpResponse<IHistoriqueChat>;
type EntityArrayResponseType = HttpResponse<IHistoriqueChat[]>;

@Injectable({ providedIn: 'root' })
export class HistoriqueChatService {
    public resourceUrl = SERVER_API_URL + 'api/historique-chats';

    constructor(protected http: HttpClient) {}

    create(historiqueChat: IHistoriqueChat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiqueChat);
        return this.http
            .post<IHistoriqueChat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(historiqueChat: IHistoriqueChat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(historiqueChat);
        return this.http
            .put<IHistoriqueChat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHistoriqueChat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHistoriqueChat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(historiqueChat: IHistoriqueChat): IHistoriqueChat {
        const copy: IHistoriqueChat = Object.assign({}, historiqueChat, {
            dateAppel:
                historiqueChat.dateAppel != null && historiqueChat.dateAppel.isValid() ? historiqueChat.dateAppel.format(DATE_FORMAT) : null
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
            res.body.forEach((historiqueChat: IHistoriqueChat) => {
                historiqueChat.dateAppel = historiqueChat.dateAppel != null ? moment(historiqueChat.dateAppel) : null;
            });
        }
        return res;
    }
}
