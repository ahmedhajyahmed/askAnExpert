import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INonDisponibilite } from 'app/shared/model/non-disponibilite.model';

@Component({
    selector: 'jhi-non-disponibilite-detail',
    templateUrl: './non-disponibilite-detail.component.html'
})
export class NonDisponibiliteDetailComponent implements OnInit {
    nonDisponibilite: INonDisponibilite;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ nonDisponibilite }) => {
            this.nonDisponibilite = nonDisponibilite;
        });
    }

    previousState() {
        window.history.back();
    }
}
