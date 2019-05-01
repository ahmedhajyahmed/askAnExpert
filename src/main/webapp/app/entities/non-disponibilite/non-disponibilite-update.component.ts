import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';
import { NonDisponibiliteService } from './non-disponibilite.service';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-non-disponibilite-update',
    templateUrl: './non-disponibilite-update.component.html'
})
export class NonDisponibiliteUpdateComponent implements OnInit {
    nonDisponibilite: INonDisponibilite;
    isSaving: boolean;

    experts: IExpert[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected nonDisponibiliteService: NonDisponibiliteService,
        protected expertService: ExpertService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ nonDisponibilite }) => {
            this.nonDisponibilite = nonDisponibilite;
            this.date = this.nonDisponibilite.date != null ? this.nonDisponibilite.date.format(DATE_TIME_FORMAT) : null;
        });
        this.expertService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExpert[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExpert[]>) => response.body)
            )
            .subscribe((res: IExpert[]) => (this.experts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.nonDisponibilite.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.nonDisponibilite.id !== undefined) {
            this.subscribeToSaveResponse(this.nonDisponibiliteService.update(this.nonDisponibilite));
        } else {
            this.subscribeToSaveResponse(this.nonDisponibiliteService.create(this.nonDisponibilite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INonDisponibilite>>) {
        result.subscribe((res: HttpResponse<INonDisponibilite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackExpertById(index: number, item: IExpert) {
        return item.id;
    }
}
