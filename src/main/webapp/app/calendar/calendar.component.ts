import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { CalendarService } from 'app/calendar/calendar.service';
import { Calendar } from '@fullcalendar/core';

@Component({
    selector: 'jhi-calendar',
    templateUrl: './calendar.component.html',
    styles: []
})
export class CalendarComponent implements OnInit {
    calendarPlugins = [dayGridPlugin, timeGridPlugin, listPlugin];
    calendarEvents: any[] = [];
    constructor(private svc: CalendarService) {}

    /*handelLoading(arg) {
        document.addEventListener('DOMContentLoaded', function() {
            const calendarEl = document.getElementById('calendar');

            const calendar = new Calendar(calendarEl, {
                header: { center: 'dayGridMonth,timeGridWeek' }, // buttons for switching between views

                views: {
                    dayGridMonth: { // name of view
                        titleFormat: 'YYYY, MM, DD'
                        // other view-specific options here
                    }
                }
            });

            calendar.render();
        });
    }*/

    ngOnInit() {
        this.svc.getData().subscribe(data => (this.calendarEvents = data));
    }
}
