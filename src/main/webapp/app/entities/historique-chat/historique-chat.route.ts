import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HistoriqueChat } from 'app/shared/model/historique-chat.model';
import { HistoriqueChatService } from './historique-chat.service';
import { HistoriqueChatComponent } from './historique-chat.component';
import { HistoriqueChatDetailComponent } from './historique-chat-detail.component';
import { HistoriqueChatUpdateComponent } from './historique-chat-update.component';
import { HistoriqueChatDeletePopupComponent } from './historique-chat-delete-dialog.component';
import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';

@Injectable({ providedIn: 'root' })
export class HistoriqueChatResolve implements Resolve<IHistoriqueChat> {
    constructor(private service: HistoriqueChatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHistoriqueChat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HistoriqueChat>) => response.ok),
                map((historiqueChat: HttpResponse<HistoriqueChat>) => historiqueChat.body)
            );
        }
        return of(new HistoriqueChat());
    }
}

export const historiqueChatRoute: Routes = [
    {
        path: '',
        component: HistoriqueChatComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'HistoriqueChats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HistoriqueChatDetailComponent,
        resolve: {
            historiqueChat: HistoriqueChatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueChats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HistoriqueChatUpdateComponent,
        resolve: {
            historiqueChat: HistoriqueChatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueChats'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HistoriqueChatUpdateComponent,
        resolve: {
            historiqueChat: HistoriqueChatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueChats'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historiqueChatPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HistoriqueChatDeletePopupComponent,
        resolve: {
            historiqueChat: HistoriqueChatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueChats'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
