/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AskAnExpertTestModule } from '../../../test.module';
import { NonDisponibiliteComponent } from 'app/entities/non-disponibilite/non-disponibilite.component';
import { NonDisponibiliteService } from 'app/entities/non-disponibilite/non-disponibilite.service';
import { NonDisponibilite } from 'app/shared/model/non-disponibilite.model';

describe('Component Tests', () => {
    describe('NonDisponibilite Management Component', () => {
        let comp: NonDisponibiliteComponent;
        let fixture: ComponentFixture<NonDisponibiliteComponent>;
        let service: NonDisponibiliteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [NonDisponibiliteComponent],
                providers: []
            })
                .overrideTemplate(NonDisponibiliteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NonDisponibiliteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NonDisponibiliteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new NonDisponibilite(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.nonDisponibilites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
