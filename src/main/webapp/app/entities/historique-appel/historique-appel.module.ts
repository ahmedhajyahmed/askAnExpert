import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AskAnExpertSharedModule } from 'app/shared';
import {
    HistoriqueAppelComponent,
    HistoriqueAppelDetailComponent,
    HistoriqueAppelUpdateComponent,
    HistoriqueAppelDeletePopupComponent,
    HistoriqueAppelDeleteDialogComponent,
    historiqueAppelRoute,
    historiqueAppelPopupRoute
} from './';

const ENTITY_STATES = [...historiqueAppelRoute, ...historiqueAppelPopupRoute];

@NgModule({
    imports: [AskAnExpertSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HistoriqueAppelComponent,
        HistoriqueAppelDetailComponent,
        HistoriqueAppelUpdateComponent,
        HistoriqueAppelDeleteDialogComponent,
        HistoriqueAppelDeletePopupComponent
    ],
    entryComponents: [
        HistoriqueAppelComponent,
        HistoriqueAppelUpdateComponent,
        HistoriqueAppelDeleteDialogComponent,
        HistoriqueAppelDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertHistoriqueAppelModule {}
