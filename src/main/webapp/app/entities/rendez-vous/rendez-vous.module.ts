import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AskAnExpertSharedModule } from 'app/shared';
import {
    RendezVousComponent,
    RendezVousDetailComponent,
    RendezVousUpdateComponent,
    RendezVousDeletePopupComponent,
    RendezVousDeleteDialogComponent,
    rendezVousRoute,
    rendezVousPopupRoute
} from './';

const ENTITY_STATES = [...rendezVousRoute, ...rendezVousPopupRoute];

@NgModule({
    imports: [AskAnExpertSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RendezVousComponent,
        RendezVousDetailComponent,
        RendezVousUpdateComponent,
        RendezVousDeleteDialogComponent,
        RendezVousDeletePopupComponent
    ],
    entryComponents: [RendezVousComponent, RendezVousUpdateComponent, RendezVousDeleteDialogComponent, RendezVousDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertRendezVousModule {}
