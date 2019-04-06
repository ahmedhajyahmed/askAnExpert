import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AskAnExpertSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { SliderComponent } from './slider/slider.component';

@NgModule({
    imports: [AskAnExpertSharedModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent, SliderComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertHomeModule {}
