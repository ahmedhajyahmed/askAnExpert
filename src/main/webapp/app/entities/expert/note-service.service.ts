import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Expert } from 'app/shared/model/expert.model';

@Injectable({
    providedIn: 'root'
})
export class NoteServiceService {
    constructor(private http: HttpClient) {}

    getData(): Observable<any> {
        return this.http.get<any>('/api/experts/{id}');
    }

    putData(expert: Expert): Observable<any> {
        return this.http.put<any>('/api/experts', expert);
    }
}
