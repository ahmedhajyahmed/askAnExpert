import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';
import { HistoriqueAppelService } from './historique-appel.service';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-historique-appel-update',
    templateUrl: './historique-appel-update.component.html'
})
export class HistoriqueAppelUpdateComponent implements OnInit {
    historiqueAppel: IHistoriqueAppel;
    isSaving: boolean;

    experts: IExpert[];
    dateAppelDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected historiqueAppelService: HistoriqueAppelService,
        protected expertService: ExpertService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ historiqueAppel }) => {
            this.historiqueAppel = historiqueAppel;
        });
        this.expertService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExpert[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExpert[]>) => response.body)
            )
            .subscribe((res: IExpert[]) => (this.experts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.historiqueAppel.id !== undefined) {
            this.subscribeToSaveResponse(this.historiqueAppelService.update(this.historiqueAppel));
        } else {
            this.subscribeToSaveResponse(this.historiqueAppelService.create(this.historiqueAppel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriqueAppel>>) {
        result.subscribe((res: HttpResponse<IHistoriqueAppel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
