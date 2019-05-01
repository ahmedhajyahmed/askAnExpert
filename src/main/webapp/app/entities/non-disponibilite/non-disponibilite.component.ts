import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';
import { AccountService } from 'app/core';
import { NonDisponibiliteService } from './non-disponibilite.service';

@Component({
    selector: 'jhi-non-disponibilite',
    templateUrl: './non-disponibilite.component.html'
})
export class NonDisponibiliteComponent implements OnInit, OnDestroy {
    nonDisponibilites: INonDisponibilite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected nonDisponibiliteService: NonDisponibiliteService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.nonDisponibiliteService
            .query()
            .pipe(
                filter((res: HttpResponse<INonDisponibilite[]>) => res.ok),
                map((res: HttpResponse<INonDisponibilite[]>) => res.body)
            )
            .subscribe(
                (res: INonDisponibilite[]) => {
                    this.nonDisponibilites = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNonDisponibilites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INonDisponibilite) {
        return item.id;
    }

    registerChangeInNonDisponibilites() {
        this.eventSubscriber = this.eventManager.subscribe('nonDisponibiliteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
