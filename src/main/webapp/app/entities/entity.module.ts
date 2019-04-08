import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'expert',
                loadChildren: './expert/expert.module#AskAnExpertExpertModule'
            },
            {
                path: 'expert',
                loadChildren: './expert/expert.module#AskAnExpertExpertModule'
            },
            {
                path: 'expert',
                loadChildren: './expert/expert.module#AskAnExpertExpertModule'
            },
            {
                path: 'expert',
                loadChildren: './expert/expert.module#AskAnExpertExpertModule'
            },
            {
                path: 'expert',
                loadChildren: './expert/expert.module#AskAnExpertExpertModule'
            },
            {
                path: 'disponibilite',
                loadChildren: './disponibilite/disponibilite.module#AskAnExpertDisponibiliteModule'
            },
            {
                path: 'historique-appel',
                loadChildren: './historique-appel/historique-appel.module#AskAnExpertHistoriqueAppelModule'
            },
            {
                path: 'historique-chat',
                loadChildren: './historique-chat/historique-chat.module#AskAnExpertHistoriqueChatModule'
            },
            {
                path: 'historique-appel',
                loadChildren: './historique-appel/historique-appel.module#AskAnExpertHistoriqueAppelModule'
            },
            {
                path: 'historique-chat',
                loadChildren: './historique-chat/historique-chat.module#AskAnExpertHistoriqueChatModule'
            },
            {
                path: 'disponibilite',
                loadChildren: './disponibilite/disponibilite.module#AskAnExpertDisponibiliteModule'
            },
            {
                path: 'historique-appel',
                loadChildren: './historique-appel/historique-appel.module#AskAnExpertHistoriqueAppelModule'
            },
            {
                path: 'historique-chat',
                loadChildren: './historique-chat/historique-chat.module#AskAnExpertHistoriqueChatModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AskAnExpertEntityModule {}
