import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHistoriqueAppel } from 'app/shared/model/historique-appel.model';

@Component({
    selector: 'jhi-historique-appel-detail',
    templateUrl: './historique-appel-detail.component.html'
})
export class HistoriqueAppelDetailComponent implements OnInit {
    historiqueAppel: IHistoriqueAppel;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ historiqueAppel }) => {
            this.historiqueAppel = historiqueAppel;
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
