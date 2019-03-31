import { NgModule } from '@angular/core';

import { AskAnExpertSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [AskAnExpertSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [AskAnExpertSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class AskAnExpertSharedCommonModule {}
