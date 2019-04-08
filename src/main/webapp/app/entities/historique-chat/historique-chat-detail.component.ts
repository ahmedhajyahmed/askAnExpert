import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHistoriqueChat } from 'app/shared/model/historique-chat.model';

@Component({
    selector: 'jhi-historique-chat-detail',
    templateUrl: './historique-chat-detail.component.html'
})
export class HistoriqueChatDetailComponent implements OnInit {
    historiqueChat: IHistoriqueChat;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiqueChat }) => {
            this.historiqueChat = historiqueChat;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
