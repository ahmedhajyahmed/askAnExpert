/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AskAnExpertTestModule } from '../../../test.module';
import { NonDisponibiliteUpdateComponent } from 'app/entities/non-disponibilite/non-disponibilite-update.component';
import { NonDisponibiliteService } from 'app/entities/non-disponibilite/non-disponibilite.service';
import { NonDisponibilite } from 'app/shared/model/non-disponibilite.model';

describe('Component Tests', () => {
    describe('NonDisponibilite Management Update Component', () => {
        let comp: NonDisponibiliteUpdateComponent;
        let fixture: ComponentFixture<NonDisponibiliteUpdateComponent>;
        let service: NonDisponibiliteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AskAnExpertTestModule],
                declarations: [NonDisponibiliteUpdateComponent]
            })
                .overrideTemplate(NonDisponibiliteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NonDisponibiliteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NonDisponibiliteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new NonDisponibilite(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nonDisponibilite = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new NonDisponibilite();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.nonDisponibilite = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
