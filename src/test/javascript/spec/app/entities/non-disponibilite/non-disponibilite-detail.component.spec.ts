/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { NonDisponibiliteDetailComponent } from 'app/entities/non-disponibilite/non-disponibilite-detail.component';
import { NonDisponibilite } from 'app/shared/model/non-disponibilite.model';

describe('Component Tests', () => {
    describe('NonDisponibilite Management Detail Component', () => {
        let comp: NonDisponibiliteDetailComponent;
        let fixture: ComponentFixture<NonDisponibiliteDetailComponent>;
        const route = ({ data: of({ nonDisponibilite: new NonDisponibilite(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [NonDisponibiliteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NonDisponibiliteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NonDisponibiliteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.nonDisponibilite).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
