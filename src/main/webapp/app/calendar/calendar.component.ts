import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { CalendarService } from 'app/calendar/calendar.service';

@Component({
    selector: 'jhi-calendar',
    templateUrl: './calendar.component.html',
    styles: []
})
export class CalendarComponent implements OnInit {
    calendarPlugins = [dayGridPlugin, timeGridPlugin, listPlugin];
    calendarEvents: any[] = [];
    constructor(private svc: CalendarService) {}

    ngOnInit() {
        this.svc.getData().subscribe(data => (this.calendarEvents = data));
    }
}
