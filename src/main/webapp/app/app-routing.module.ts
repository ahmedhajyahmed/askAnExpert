import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { CalendarComponent } from 'app/calendar/calendar.component';
import { NoteComponent } from 'app/note/note.component';
const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {
                    path: 'Note',
                    component: NoteComponent
                },
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#AskAnExpertAdminModule'
                },
                {
                    path: 'calendar',
                    component: CalendarComponent
                },
                ...LAYOUT_ROUTES
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule]
})
export class AskAnExpertAppRoutingModule {}
