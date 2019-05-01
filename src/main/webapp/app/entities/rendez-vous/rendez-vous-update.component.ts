import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRendezVous } from 'app/shared/model/rendez-vous.model';
import { RendezVousService } from './rendez-vous.service';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-rendez-vous-update',
    templateUrl: './rendez-vous-update.component.html'
})
export class RendezVousUpdateComponent implements OnInit {
    rendezVous: IRendezVous;
    isSaving: boolean;

    experts: IExpert[];
    startDp: any;
    endDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rendezVousService: RendezVousService,
        protected expertService: ExpertService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rendezVous }) => {
            this.rendezVous = rendezVous;
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
        if (this.rendezVous.id !== undefined) {
            this.subscribeToSaveResponse(this.rendezVousService.update(this.rendezVous));
        } else {
            this.subscribeToSaveResponse(this.rendezVousService.create(this.rendezVous));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRendezVous>>) {
        result.subscribe((res: HttpResponse<IRendezVous>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
