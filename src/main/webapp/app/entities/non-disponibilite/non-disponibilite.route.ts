import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NonDisponibilite } from 'app/shared/model/non-disponibilite.model';
import { NonDisponibiliteService } from './non-disponibilite.service';
import { NonDisponibiliteComponent } from './non-disponibilite.component';
import { NonDisponibiliteDetailComponent } from './non-disponibilite-detail.component';
import { NonDisponibiliteUpdateComponent } from './non-disponibilite-update.component';
import { NonDisponibiliteDeletePopupComponent } from './non-disponibilite-delete-dialog.component';
import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';

@Injectable({ providedIn: 'root' })
export class NonDisponibiliteResolve implements Resolve<INonDisponibilite> {
    constructor(private service: NonDisponibiliteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INonDisponibilite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NonDisponibilite>) => response.ok),
                map((nonDisponibilite: HttpResponse<NonDisponibilite>) => nonDisponibilite.body)
            );
        }
        return of(new NonDisponibilite());
    }
}

export const nonDisponibiliteRoute: Routes = [
    {
        path: '',
        component: NonDisponibiliteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NonDisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NonDisponibiliteDetailComponent,
        resolve: {
            nonDisponibilite: NonDisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NonDisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NonDisponibiliteUpdateComponent,
        resolve: {
            nonDisponibilite: NonDisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NonDisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NonDisponibiliteUpdateComponent,
        resolve: {
            nonDisponibilite: NonDisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NonDisponibilites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nonDisponibilitePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NonDisponibiliteDeletePopupComponent,
        resolve: {
            nonDisponibilite: NonDisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'NonDisponibilites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
