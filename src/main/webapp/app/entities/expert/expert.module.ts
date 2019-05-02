import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AskAnExpertSharedModule } from 'app/shared';
import {
    ExpertComponent,
    ExpertDetailComponent,
    ExpertUpdateComponent,
    ExpertDeletePopupComponent,
    ExpertDeleteDialogComponent,
    expertRoute,
    expertPopupRoute,
    AffichageAvanceComponent
} from './';

const ENTITY_STATES = [...expertRoute, ...expertPopupRoute];

@NgModule({
    imports: [AskAnExpertSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExpertComponent,
        ExpertDetailComponent,
        ExpertUpdateComponent,
        AffichageAvanceComponent,
        ExpertDeleteDialogComponent,
        ExpertDeletePopupComponent
    ],
    entryComponents: [
        ExpertComponent,
        ExpertUpdateComponent,
        ExpertDeleteDialogComponent,
        AffichageAvanceComponent,
        ExpertDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertExpertModule {}
