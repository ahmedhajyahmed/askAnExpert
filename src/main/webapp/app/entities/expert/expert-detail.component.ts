import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExpert, Expert } from 'app/shared/model/expert.model';
import { NoteServiceService } from './note-service.service';
import { expertPopupRoute } from './expert.route';
import { DOCUMENT } from '@angular/common';
import { notEqual } from 'assert';
@Component({
    selector: 'jhi-expert-detail',
    templateUrl: './expert-detail.component.html',
    styleUrls: ['expert-detail.css']
})
export class ExpertDetailComponent implements OnInit {
    expert: IExpert;
    currentRate = 6;
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, private svc: NoteServiceService) {}

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
    methode(): void {
        this.expert.note = (this.expert.note + this.currentRate) / 2;
        this.svc.putData(this.expert).subscribe();
    }
}
