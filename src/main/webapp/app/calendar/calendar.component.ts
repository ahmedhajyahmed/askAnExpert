import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import listPlugin from '@fullcalendar/list';
import { CalendarService } from 'app/calendar/calendar.service';
import { Calendar } from '@fullcalendar/core';
import { IRendezVous, RendezVous } from 'app/shared/model/rendez-vous.model';
import { Moment } from 'moment';
import * as moment from 'moment';
import { Expert, IExpert } from 'app/shared/model/expert.model';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['calendar.css']
})
export class CalendarComponent implements OnInit {
    calendarPlugins = [dayGridPlugin, timeGridPlugin, listPlugin];
    calendarEvents: any[] = [];
    rendezVous: IRendezVous;
    expert: any;
    constructor(private svc: CalendarService) {}

    handleEventClick(info) {
        // handler method
        // const popup = document.getElementById('pop-up');
        // popup.style.display = 'block';

        alert('Event: ' + info.event.title);
        alert('Coordinates: ' + info.jsEvent.pageX + ',' + info.jsEvent.pageY);
        alert('View: ' + info.view.type);
        info.el.style.borderColor = 'red';
        info.jsEvent.preventDefault();
    }
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

    putData(arg) {
        const title = (<HTMLInputElement>document.getElementById('title')).value;
        const date = (<HTMLInputElement>document.getElementById('date')).value;
        //alert(title+ date);
        this.rendezVous = new RendezVous();
        this.rendezVous.title = title;
        this.rendezVous.start = moment(date);
        this.svc.getExpert().subscribe(expert => (this.expert = expert));
        this.rendezVous.expert = this.expert;
        alert(this.rendezVous.expert.id);
        this.svc.postData(this.rendezVous).subscribe();
        window.location.reload();
    }

    ngOnInit() {
        this.svc.getData().subscribe(data => (this.calendarEvents = data));
    }
}
