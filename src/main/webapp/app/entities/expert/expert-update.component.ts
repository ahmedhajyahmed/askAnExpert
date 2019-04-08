import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiDataUtils } from 'ng-jhipster';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from './expert.service';

@Component({
    selector: 'jhi-expert-update',
    templateUrl: './expert-update.component.html'
})
export class ExpertUpdateComponent implements OnInit {
    expert: IExpert;
    isSaving: boolean;
    dateNaissanceDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected expertService: ExpertService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ expert }) => {
            this.expert = expert;
        });
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.expert, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.expert.id !== undefined) {
            this.subscribeToSaveResponse(this.expertService.update(this.expert));
        } else {
            this.subscribeToSaveResponse(this.expertService.create(this.expert));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpert>>) {
        result.subscribe((res: HttpResponse<IExpert>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
