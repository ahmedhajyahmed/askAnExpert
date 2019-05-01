import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AskAnExpertSharedModule } from 'app/shared';
import {
    NonDisponibiliteComponent,
    NonDisponibiliteDetailComponent,
    NonDisponibiliteUpdateComponent,
    NonDisponibiliteDeletePopupComponent,
    NonDisponibiliteDeleteDialogComponent,
    nonDisponibiliteRoute,
    nonDisponibilitePopupRoute
} from './';

const ENTITY_STATES = [...nonDisponibiliteRoute, ...nonDisponibilitePopupRoute];

@NgModule({
    imports: [AskAnExpertSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NonDisponibiliteComponent,
        NonDisponibiliteDetailComponent,
        NonDisponibiliteUpdateComponent,
        NonDisponibiliteDeleteDialogComponent,
        NonDisponibiliteDeletePopupComponent
    ],
    entryComponents: [
        NonDisponibiliteComponent,
        NonDisponibiliteUpdateComponent,
        NonDisponibiliteDeleteDialogComponent,
        NonDisponibiliteDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertNonDisponibiliteModule {}
