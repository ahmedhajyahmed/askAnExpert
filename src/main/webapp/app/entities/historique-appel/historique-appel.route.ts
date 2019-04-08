import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HistoriqueAppel } from 'app/shared/model/historique-appel.model';
import { HistoriqueAppelService } from './historique-appel.service';
import { HistoriqueAppelComponent } from './historique-appel.component';
import { HistoriqueAppelDetailComponent } from './historique-appel-detail.component';
import { HistoriqueAppelUpdateComponent } from './historique-appel-update.component';
import { HistoriqueAppelDeletePopupComponent } from './historique-appel-delete-dialog.component';
import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';

@Injectable({ providedIn: 'root' })
export class HistoriqueAppelResolve implements Resolve<IHistoriqueAppel> {
    constructor(private service: HistoriqueAppelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHistoriqueAppel> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HistoriqueAppel>) => response.ok),
                map((historiqueAppel: HttpResponse<HistoriqueAppel>) => historiqueAppel.body)
            );
        }
        return of(new HistoriqueAppel());
    }
}

export const historiqueAppelRoute: Routes = [
    {
        path: '',
        component: HistoriqueAppelComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'HistoriqueAppels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HistoriqueAppelDetailComponent,
        resolve: {
            historiqueAppel: HistoriqueAppelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueAppels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HistoriqueAppelUpdateComponent,
        resolve: {
            historiqueAppel: HistoriqueAppelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueAppels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HistoriqueAppelUpdateComponent,
        resolve: {
            historiqueAppel: HistoriqueAppelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueAppels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historiqueAppelPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HistoriqueAppelDeletePopupComponent,
        resolve: {
            historiqueAppel: HistoriqueAppelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HistoriqueAppels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
