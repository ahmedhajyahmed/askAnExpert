import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExpert } from 'app/shared/model/expert.model';

@Component({
    selector: 'jhi-expert-detail',
    templateUrl: './expert-detail.component.html'
})
export class ExpertDetailComponent implements OnInit {
    expert: IExpert;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
