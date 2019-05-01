import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRendezVous } from 'app/shared/model/rendez-vous.model';

@Component({
    selector: 'jhi-rendez-vous-detail',
    templateUrl: './rendez-vous-detail.component.html'
})
export class RendezVousDetailComponent implements OnInit {
    rendezVous: IRendezVous;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rendezVous }) => {
            this.rendezVous = rendezVous;
        });
    }

    previousState() {
        window.history.back();
    }
}
