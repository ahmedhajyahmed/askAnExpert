import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RendezVous } from 'app/shared/model/rendez-vous.model';

@Injectable({
    providedIn: 'root'
})
export class CalendarService {
    constructor(private http: HttpClient) {}
    getData(): Observable<any[]> {
        // return this.http.get<any[]>('content/events.json');
        return this.http.get<any[]>('/api/rendez-vous');
    }

    getExpert(): Observable<any> {
        return this.http.get<any>('/api/experts/1');
    }
    postData(rendezVous: RendezVous): Observable<any> {
        return this.http.post<any>('/api/rendez-vous', rendezVous);
    }
}
