import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDisponibilite } from 'app/shared/model/disponibilite.model';
import { DisponibiliteService } from './disponibilite.service';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-disponibilite-update',
    templateUrl: './disponibilite-update.component.html'
})
export class DisponibiliteUpdateComponent implements OnInit {
    disponibilite: IDisponibilite;
    isSaving: boolean;

    experts: IExpert[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected disponibiliteService: DisponibiliteService,
        protected expertService: ExpertService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ disponibilite }) => {
            this.disponibilite = disponibilite;
            this.date = this.disponibilite.date != null ? this.disponibilite.date.format(DATE_TIME_FORMAT) : null;
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
        this.disponibilite.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.disponibilite.id !== undefined) {
            this.subscribeToSaveResponse(this.disponibiliteService.update(this.disponibilite));
        } else {
            this.subscribeToSaveResponse(this.disponibiliteService.create(this.disponibilite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisponibilite>>) {
        result.subscribe((res: HttpResponse<IDisponibilite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
