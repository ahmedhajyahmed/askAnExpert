import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';
import { HistoriqueChatService } from './historique-chat.service';
import { IExpert } from 'app/shared/model/expert.model';
import { ExpertService } from 'app/entities/expert';

@Component({
    selector: 'jhi-historique-chat-update',
    templateUrl: './historique-chat-update.component.html'
})
export class HistoriqueChatUpdateComponent implements OnInit {
    historiqueChat: IHistoriqueChat;
    isSaving: boolean;

    experts: IExpert[];
    dateAppelDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected historiqueChatService: HistoriqueChatService,
        protected expertService: ExpertService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ historiqueChat }) => {
            this.historiqueChat = historiqueChat;
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
        if (this.historiqueChat.id !== undefined) {
            this.subscribeToSaveResponse(this.historiqueChatService.update(this.historiqueChat));
        } else {
            this.subscribeToSaveResponse(this.historiqueChatService.create(this.historiqueChat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoriqueChat>>) {
        result.subscribe((res: HttpResponse<IHistoriqueChat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
